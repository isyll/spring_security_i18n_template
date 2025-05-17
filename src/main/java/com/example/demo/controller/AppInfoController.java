package com.example.demo.controller;

import com.example.demo.config.constants.AppInfo;
import com.example.demo.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
@Tag(name = "Info API", description = "API to get app information.")
public class AppInfoController {

  @Operation(
      summary = "Get app information",
      description = "This endpoint provides application information.")
  @GetMapping
  public ResponseEntity<ApiResponse<com.example.demo.dto.response.AppInfo>> getAppInfo() {
    List<String> locales = new ArrayList<>();
    AppInfo.SUPPORTED_LOCALES.forEach((locale) -> locales.add(locale.toString()));
    com.example.demo.dto.response.AppInfo appInfo =
        new com.example.demo.dto.response.AppInfo(
            AppInfo.APP_NAME, AppInfo.APP_VERSION, AppInfo.APP_DESCRIPTION, locales);
    return ApiResponse.success(appInfo).toResponseEntity();
  }
}
