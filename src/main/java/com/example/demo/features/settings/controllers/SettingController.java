package com.example.demo.features.settings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.core.payload.ApiResponse;
import com.example.demo.features.settings.models.Setting;
import com.example.demo.features.settings.services.SettingService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/app/settings")
@Tag(name = "Application settings", description = "The Application settings API")
public class SettingController {

    @Autowired
    SettingService settingService;

    @GetMapping
    public ResponseEntity<ApiResponse<Setting>> getSetting(
            @RequestParam(name = "key", required = true) String key) {
        Setting setting = settingService.getSetting(key);
        return ApiResponse.success(setting).toReponseEntity();
    }
}
