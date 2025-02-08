package com.example.demo.features.app_info.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.core.constants.AppConfig;
import com.example.demo.core.payload.ApiResponse;
import com.example.demo.features.app_info.dto.AppInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/info")
@Tag(name = "Info API", description = "API to get app informations.")
public class AppInfoController {

    @Operation(summary = "Get app informations", description = "This endpoint provides application information.")
    @GetMapping
    public ResponseEntity<ApiResponse<AppInfo>> getAppInfo() {
        List<String> locales = new ArrayList<>();
        AppConfig.SUPPORTED_LOCALES.forEach((locale) -> locales.add(locale.toString()));
        AppInfo appInfo = new AppInfo(AppConfig.APP_NAME, AppConfig.APP_VERSION,
                AppConfig.APP_DESCRIPTION, locales);
        return ApiResponse.success(appInfo).toReponseEntity();
    }
}
