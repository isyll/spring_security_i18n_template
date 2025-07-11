package com.example.demo.controller;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.response.SuccessResponse;
import com.example.demo.dto.auth.ChangePasswordRequest;
import com.example.demo.model.User;
import com.example.demo.service.AccountService;
import com.example.demo.utils.Translator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "Account management", value = "/account")
@Tag(
    name = "Account Management",
    description = "Operations related to the logged-in user's own account")
@RequiredArgsConstructor
public class AccountController extends BaseController {

  private final AccountService accountService;
  private final Translator translator;

  @Operation(
      summary = "Get current user profile",
      description = "Returns the profile information of the currently authenticated user")
  @GetMapping("/profile")
  public ResponseEntity<ApiResponse<User>> getProfile() {
    return ok(currentUser());
  }

  @PostMapping("/change-password")
  @Operation(
      summary = "Change user password",
      description =
          """
          Allows the authenticated user to update their password by providing the current and new passwords.
          Validates the old password and enforces password policy for the new password.""")
  public ResponseEntity<SuccessResponse> changePassword(
      @RequestBody @Valid ChangePasswordRequest request) {
    accountService.changePassword(request);
    return ok(translator.t("message.password_changed"));
  }
}
