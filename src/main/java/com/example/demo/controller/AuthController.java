package com.example.demo.controller;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.response.SuccessResponse;
import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.RefreshTokenRequest;
import com.example.demo.dto.response.TokenPair;
import com.example.demo.dto.user.admin.CreateAdminRequest;
import com.example.demo.exceptions.ForbiddenOperationException;
import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import com.example.demo.service.AuthService;
import com.example.demo.utils.SecurityUtils;
import com.example.demo.utils.Translator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(name = "Authentication", value = "/auth")
@RestController
@Tag(name = "Authentication", description = "API to manage authentication.")
@RequiredArgsConstructor
public class AuthController extends BaseController {

  private final AuthService authService;
  private final AdminService adminService;
  private final Translator translator;

  @PostMapping("/login")
  @Operation(summary = "Login", description = "Log in with registration number and password.")
  public ResponseEntity<ApiResponse<TokenPair>> login(@RequestBody @Valid LoginRequest request) {
    return ok(authService.authenticate(request));
  }

  @PostMapping("/logout")
  @Operation(
      summary = "Logout",
      description = "Log out the current user and invalidate the access token.")
  public ResponseEntity<SuccessResponse> logout(@RequestHeader("Authorization") String authHeader) {
    if (SecurityUtils.isUnauthenticated()) {
      throw new ForbiddenOperationException();
    }
    String token = authHeader.replace("Bearer ", "");
    authService.blacklistToken(token);
    return ok(translator.t("message.logout"));
  }

  @PostMapping("/refresh")
  @Operation(summary = "Refresh token", description = "Get new access token from refresh token.")
  public ResponseEntity<ApiResponse<TokenPair>> refreshToken(
      @RequestBody @Valid RefreshTokenRequest request) {
    return ok(authService.authenticateFromRefreshToken(request));
  }

  @PostMapping("/signup")
  @Operation(
      summary = "Sign up a new admin",
      description = "Creates a new admin account with the given credentials and details.")
  public ResponseEntity<ApiResponse<Admin>> signup(@RequestBody @Valid CreateAdminRequest request) {
    return ok(adminService.createAdmin(request));
  }
}
