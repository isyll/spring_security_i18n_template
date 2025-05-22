package com.example.demo.controller;

import com.example.demo.config.constants.AppConstants;
import com.example.demo.config.i18n.I18nUtil;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.AppInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Info API", description = "API to get app information.")
public class AppInfoController extends BaseController {

  private final I18nUtil i18nUtil;

  public AppInfoController(I18nUtil i18nUtil) {
    this.i18nUtil = i18nUtil;
  }

  @Operation(summary = "Greeting")
  @GetMapping("/")
  public ResponseEntity<ApiResponse<Object>> greeting() {
    String message = i18nUtil.getMessage("message.greeting");
    return ok(message);
  }

  @Operation(
      summary = "Get app information",
      description = "This endpoint provides application information.")
  @GetMapping("/info")
  public ResponseEntity<ApiResponse<AppInfo>> getAppInfo() {
    List<String> locales = new ArrayList<>();
    AppConstants.SUPPORTED_LOCALES.forEach((locale) -> locales.add(locale.toString()));
    return ok(
        new AppInfo(
            AppConstants.APP_NAME,
            AppConstants.APP_VERSION,
            AppConstants.APP_DESCRIPTION,
            locales));
  }
}
