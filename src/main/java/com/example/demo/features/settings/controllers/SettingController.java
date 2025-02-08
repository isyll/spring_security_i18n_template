package com.example.demo.features.settings.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.core.payload.ApiResponse;
import com.example.demo.features.settings.dto.SettingValue;
import com.example.demo.features.settings.dto.UpdateSettingByKeyDto;
import com.example.demo.features.settings.models.Setting;
import com.example.demo.features.settings.services.SettingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/app/settings")
@Tag(name = "Application settings", description = "The Application settings API.")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @Operation(summary = "Get a setting by key")
    @GetMapping("/{key}")
    public ResponseEntity<ApiResponse<Setting>> getSetting(@PathVariable String key) {
        Setting setting = settingService.getSetting(key);
        return ApiResponse.success(setting).toReponseEntity();
    }

    @Operation(summary = "Get all settings")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Setting>>> getSettings() {
        List<Setting> settings = settingService.getAllSettings();
        return ApiResponse.success(settings).toReponseEntity();
    }

    @Operation(summary = "Get multiple settings")
    @GetMapping("/by-keys")
    public ResponseEntity<ApiResponse<List<Setting>>> search(@RequestParam(required = true) String keys) {
        List<String> keysList = Arrays.asList(keys.split(","));
        List<Setting> settings = settingService.getAllSettingsInKeys(keysList);
        return ApiResponse.success(settings).toReponseEntity();
    }

    @Operation(summary = "Update setting value")
    @PutMapping("/{key}")
    public ResponseEntity<ApiResponse<Setting>> updateSetting(
            @PathVariable String key, @RequestBody UpdateSettingByKeyDto dto) {
        Setting setting = settingService.updateSetting(key, dto.getValue());
        return ApiResponse.success(setting).toReponseEntity();
    }

    @Operation(summary = "Update multiple setting values")
    @PutMapping
    public ResponseEntity<ApiResponse<List<Setting>>> updateSettings(
            @Valid @RequestBody @NotNull(message = "{validation.data_is_missing}") @NotEmpty(message = "{validation.data_is_empty}") List<SettingValue> settingValues) {
        List<Setting> settings = settingService.updateSettings(settingValues);
        return ApiResponse.success(settings).toReponseEntity();
    }
}
