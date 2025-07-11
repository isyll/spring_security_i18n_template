package com.example.demo.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
    @JsonProperty("old_password") @NotBlank(message = "{validation.old_password_cannot_be_empty}")
        String oldPassword,
    @JsonProperty("new_password")
        @NotBlank(message = "{validation.new_password_cannot_be_empty}")
        @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "{validation.password_is_invalid}")
        @Size(min = 8, message = "{validation.password.min}")
        @Size(max = 20, message = "{validation.password.max}")
        String newPassword) {}
