package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.constants.AppConfig;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/info")
@Tag(name = "Info API", description = "API to get app informations.")
public class AppInfoController {

    @GetMapping
    public Map<String, Object> getAppInfo() {
        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("name", AppConfig.APP_NAME);
        appInfo.put("version", AppConfig.APP_VERSION);
        appInfo.put("description", AppConfig.APP_DESCRIPTION);
        appInfo.put("supported_locales", AppConfig.SUPPORTED_LOCALES);
        return appInfo;
    }
}
