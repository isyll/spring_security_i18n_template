package com.example.demo.controller;

import com.example.demo.common.response.SuccessResponse;
import com.example.demo.dto.auth.ConfirmResetPasswordRequest;
import com.example.demo.dto.auth.ResetPasswordRequest;
import com.example.demo.service.PasswordResetService;
import com.example.demo.utils.Translator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "Password Reset", value = "/reset-password")
@Tag(
    name = "Password Reset",
    description = "Endpoints for requesting and confirming user password resets.")
@RequiredArgsConstructor
public class PasswordResetController extends BaseController {

  private final Translator translator;
  private final PasswordResetService passwordResetService;

  @PostMapping("/request")
  @Operation(
      summary = "Request password reset",
      description = "Sends a reset link to the provided email address.")
  public ResponseEntity<SuccessResponse> requestReset(
      @RequestBody @Valid ResetPasswordRequest request) {
    passwordResetService.requestReset(request.email());
    return ok(translator.t("message.password_reset_email_sent"));
  }

  @PostMapping("/confirm")
  @Operation(
      summary = "Confirm password reset",
      description = "Resets the user's password using a secure token.")
  public ResponseEntity<SuccessResponse> confirmReset(
      @RequestBody @Valid ConfirmResetPasswordRequest request) {
    passwordResetService.confirmReset(request.token(), request.newPassword(), request.email());
    return ok(translator.t("message.password_reset_confirm_success"));
  }
}
