package com.example.demo.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
    @NotBlank(message = "{validation.email_is_mandatory}")
        @Email(message = "{validation.email_is_invalid}")
        @Size(max = 255, message = "{validation.email_max_length}")
        String email) {}
