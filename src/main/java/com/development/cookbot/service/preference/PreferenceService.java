package com.development.cookbot.service.preference;

import com.development.cookbot.dto.client.UserPrincipalDto;
import com.development.cookbot.dto.preference.PreferenceDto;
import com.development.cookbot.dto.setting.SettingDto;
import com.development.cookbot.entity.PreferenceEntity;
import com.development.cookbot.entity.SettingEntity;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.exception.NotFoundException;
import com.development.cookbot.repository.preference.PreferenceRepository;
import com.development.cookbot.repository.user.UserRepository;
import com.development.cookbot.service.client.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private List<PreferenceDto> associatePreferences(
            UserPrincipalDto userPrincipalDto,
            List<PreferenceDto> preferenceDtoInput
    ) {

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

        // Crée les préférences inexistantes en DB
        for (PreferenceDto dto : preferenceDtoInput) {

            String allergen = dto.getAllergen();
            PreferenceEntity existing = persistedByName.get(allergen);

            if (existing == null) {
                // On la crée
                existing = preferenceRepository.save(
                        PreferenceEntity.builder()
                                .allergen(allergen)
                                .build()
                );
                persistedByName.put(allergen, existing);
            }

            cleanedPreferences.add(dto); // On garde toutes les préférences demandées
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

}
