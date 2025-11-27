package com.development.cookbot.service.preference;

import com.development.cookbot.dto.client.UserPrincipalDto;
import com.development.cookbot.dto.preference.PreferenceDto;
import com.development.cookbot.dto.setting.SettingDto;
import com.development.cookbot.entity.PreferenceEntity;
import com.development.cookbot.entity.Role;
import com.development.cookbot.entity.SettingEntity;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.exception.NotFoundException;
import com.development.cookbot.repository.preference.PreferenceRepository;
import com.development.cookbot.repository.user.UserRepository;
import com.development.cookbot.service.client.AuthenticationService;
import com.development.cookbot.service.preference.constant.PreferenceConstant;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PreferenceService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreferenceRepository preferenceRepository;

    public List<PreferenceDto> getPreferenceByUserId() {
        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();

        if(userPrincipalDto == null) {
            throw new RuntimeException("User not found");
        }

        List<PreferenceEntity> preferenceEntities = userRepository.findById(userPrincipalDto.getId())
                .get().getPreferences();

        List<PreferenceDto> preferenceDtos = new ArrayList<>();

        for (PreferenceEntity preferenceEntity:preferenceEntities) {
            PreferenceDto preferenceDto = PreferenceDto.builder()
                    .allergen(preferenceEntity.getAllergen())
                    .build();

            preferenceDtos.add(preferenceDto);
        }

        return preferenceDtos;

    }

    private List<PreferenceDto> associatePreferences(UserPrincipalDto userPrincipalDto, List<PreferenceDto> preferenceDtoInput)
    {

        UserEntity userEntity = userRepository.findById(userPrincipalDto.getId()).get();


        Map<String, PreferenceEntity> persistedByName = preferenceRepository.findAll().stream()
                .collect(Collectors.toMap(
                        PreferenceEntity::getAllergen,
                        p -> p
                ));

        Set<String> userCurrentPreferenceNames = userEntity.getPreferences().stream()
                .map(PreferenceEntity::getAllergen)
                .collect(Collectors.toSet());

        List<PreferenceEntity> preferencesToAdd = new ArrayList<>();

        for (PreferenceDto dto : preferenceDtoInput) {

            String prefName = dto.getAllergen();

            // Si déjà associé → skip
            if (userCurrentPreferenceNames.contains(prefName)) {
                continue;
            }

            PreferenceEntity prefEntity = persistedByName.get(prefName);

            preferencesToAdd.add(prefEntity);
        }

        userEntity.getPreferences().addAll(preferencesToAdd);
        userRepository.save(userEntity);

        return getPreferenceByUserId();
    }

    public String switchToTrial() {

        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();

        if(userPrincipalDto == null) {
            throw new NotFoundException("User not found");
        }

        UserEntity userEntity = userRepository.findById(userPrincipalDto.getId()).get();

        if(userEntity.getSetting().getEndedTrialDate() == null && userEntity.getRole() == Role.FREE) {
            userEntity.getSetting().setEndedTrialDate(LocalDate.now().plusDays(PreferenceConstant.TRAIL_ENDED_DAY));
            userEntity.getSetting().setTrial(true);
            userEntity.setRole(Role.PREMIUM);
            userRepository.save(userEntity);
            return "Your trial period has started for " + PreferenceConstant.TRAIL_ENDED_DAY + " days";
        }

        throw new RuntimeException("User already has a trial");
    }

    public List<PreferenceDto> deleteUserPreferenceByName(PreferenceDto preferenceDtoInput) {
        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        if(userPrincipalDto == null) {
            throw new NotFoundException("User not found");
        }

        UserEntity userEntity = userRepository.findById(userPrincipalDto.getId()).get();
        userEntity.getPreferences().removeIf(preferenceEntity -> preferenceEntity.getAllergen().equals(preferenceDtoInput.getAllergen()));
        userRepository.save(userEntity);

        return this.getPreferenceByUserId();
    }

    public List<PreferenceDto> deleteUserPreferenceById(Long preferenceIdToDelete) {
        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        PreferenceEntity preferenceEntityToDelete = preferenceRepository.findById(preferenceIdToDelete).orElseThrow(() -> new RuntimeException("Preference not found with id: " + preferenceIdToDelete));

        if(userPrincipalDto == null) {
            throw new NotFoundException("User not found");
        }

        UserEntity userEntity = userRepository.findById(userPrincipalDto.getId()).get();
        userEntity.getPreferences()
                .removeIf(preferenceEntity -> preferenceEntity.getAllergen()
                        .equals(preferenceEntityToDelete.getAllergen()));
        userRepository.save(userEntity);

        return this.getPreferenceByUserId();
    }

    public List<PreferenceDto> createPreferenceByUserId(List<PreferenceDto> preferenceDtoInput) {
        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();

        if (userPrincipalDto == null) {
            throw new RuntimeException("User not found");
        }

        // Récupère les préférences existantes dans la DB
        Map<String, PreferenceEntity> persistedByName =
                preferenceRepository.findAll().stream()
                        .collect(Collectors.toMap(PreferenceEntity::getAllergen, p -> p));

        List<PreferenceDto> cleanedPreferences = new ArrayList<>();
        final int LEVENSHTEIN_TOLERANCE = 2; // Seuil de tolérance (ajustez selon vos besoins)

        // Crée les préférences inexistantes en DB
        for (PreferenceDto dto : preferenceDtoInput) {

            String inputAllergen = dto.getAllergen();
            PreferenceEntity existing = null;

            // 1. Recherche de la préférence existante la plus proche (Levenshtein)
            Optional<PreferenceEntity> closestMatch = findClosestPreference(
                    inputAllergen,
                    persistedByName,
                    LEVENSHTEIN_TOLERANCE
            );

            if (closestMatch.isPresent()) {
                existing = closestMatch.get();
                // Optionnel: Mettre à jour le DTO pour qu'il corresponde au nom standardisé en DB
                dto.setAllergen(existing.getAllergen());
            }

            if (existing == null) {
                // 2. Si aucune correspondance proche n'est trouvée, on la crée
                existing = preferenceRepository.save(
                        PreferenceEntity.builder()
                                .allergen(inputAllergen)
                                .build()
                );
                // Ajouter à la map pour les vérifications suivantes dans la même boucle
                persistedByName.put(inputAllergen, existing);
            }

            cleanedPreferences.add(dto); // On ajoute l'objet (potentiellement modifié avec le nom standardisé)
        }

        return associatePreferences(userPrincipalDto, cleanedPreferences);
    }

    public SettingDto getSettingByUserId() {
        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        if(userPrincipalDto == null) {
            throw new NotFoundException("User not found");
        }

        UserEntity userEntity = userRepository.findById(userPrincipalDto.getId()).get();
        SettingEntity settingEntity = userEntity.getSetting();

        return SettingDto.builder()
                .darkMode(settingEntity.isDarkMode())
                .language(settingEntity.getLanguage())
                .nbPeople(settingEntity.getNbPeople())
                .build();

    }

    public SettingDto updateSettingByUserId(SettingDto settingDto) {
        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        if(userPrincipalDto == null) {
            throw new NotFoundException("User not found");
        }

        UserEntity userEntity = userRepository.findById(userPrincipalDto.getId()).get();
        SettingEntity settingEntity = userEntity.getSetting();


        if (settingDto.getLanguage() != null) {
            settingEntity.setLanguage(settingDto.getLanguage());
        }

        if (settingDto.getNbPeople() != null) {
            settingEntity.setNbPeople(settingDto.getNbPeople());
        }

        if (settingDto.getDarkMode() != null ) { // si Boolean et pas boolean
            settingEntity.setDarkMode(settingDto.getDarkMode());
        }

        userEntity.setSetting(settingEntity);
        userRepository.save(userEntity);

        return SettingDto.builder()
                .nbPeople(userEntity.getSetting().getNbPeople())
                .language(userEntity.getSetting().getLanguage())
                .darkMode(userEntity.getSetting().isDarkMode())
                .build();

    }

    private Optional<PreferenceEntity> findClosestPreference(String inputAllergen, Map<String, PreferenceEntity> persistedByName, int maxDistance) {
        LevenshteinDistance distance = new LevenshteinDistance();
        int minDistance = Integer.MAX_VALUE;
        PreferenceEntity closestMatch = null;

        for (String persistedAllergen : persistedByName.keySet()) {
            int currentDistance = distance.apply(inputAllergen, persistedAllergen);

            if (currentDistance == 0) {
                return Optional.of(persistedByName.get(persistedAllergen));
            }

            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                closestMatch = persistedByName.get(persistedAllergen);
            }
        }

        if (minDistance <= maxDistance) {
            return Optional.of(closestMatch);
        }

        return Optional.empty();
    }

}
