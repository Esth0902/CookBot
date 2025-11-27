package com.development.cookbot.controller.user;

import com.development.cookbot.dto.preference.PreferenceDto;
import com.development.cookbot.dto.setting.SettingDto;
import com.development.cookbot.exception.utils.Response;
import com.development.cookbot.service.preference.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    @Autowired
    private PreferenceService preferenceService;

    @GetMapping("/preferences")
    public ResponseEntity<Object> getUserPreferences() {
        List<PreferenceDto> preferenceDtos = preferenceService.getPreferenceByUserId();
        Response<List<PreferenceDto>> response = Response.<List<PreferenceDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(preferenceDtos)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/preferences")
    public ResponseEntity<Object> createUserPreferences(@RequestBody List<PreferenceDto> preferenceDtosInput) {
        List<PreferenceDto> preferenceDtosRes = preferenceService.createPreferenceByUserId(preferenceDtosInput);
        Response<List<PreferenceDto>> response = Response.<List<PreferenceDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(preferenceDtosRes)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/preference/name/{preferenceName}")
    public ResponseEntity<Object> deleteUserPreferencesByName(@PathVariable PreferenceDto preferenceName) {
        List<PreferenceDto> preferenceDtoRes = preferenceService.deleteUserPreferenceByName(preferenceName);
        Response<List<PreferenceDto>> response = Response.<List<PreferenceDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(preferenceDtoRes)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/preference/id/{preferenceIdToDelete}")
    public ResponseEntity<Object> deleteUserPreferencesByName(@PathVariable Long preferenceIdToDelete) {
        List<PreferenceDto> preferenceDtoRes = preferenceService.deleteUserPreferenceById(preferenceIdToDelete);
        Response<List<PreferenceDto>> response = Response.<List<PreferenceDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(preferenceDtoRes)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/settings")
    public ResponseEntity<Object> getUserSetting() {
        SettingDto settingDto = preferenceService.getSettingByUserId();
        Response<SettingDto> response = Response.<SettingDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(settingDto)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/settings")
    public ResponseEntity<Object> updateUserSetting(@RequestBody SettingDto settingDto) {
        SettingDto settingDtoRes = preferenceService.updateSettingByUserId(settingDto);
        Response<SettingDto> response = Response.<SettingDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(settingDtoRes)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trial")
    public ResponseEntity<Object> switchToTrial() {
        String trialResponse = preferenceService.switchToTrial();
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(trialResponse)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

}
