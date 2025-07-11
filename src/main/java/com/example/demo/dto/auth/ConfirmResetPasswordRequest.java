package com.example.demo.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ConfirmResetPasswordRequest(
    @NotBlank(message = "{validation.token_is_mandatory}") String token,
    @JsonProperty("new_password") @NotBlank(message = "{validation.new_password_is_mandatory}")
        String newPassword,
    @Email(message = "{validation.email_is_invalid}")
        @NotBlank(message = "{validation.email_is_mandatory}")
        String email) {}
