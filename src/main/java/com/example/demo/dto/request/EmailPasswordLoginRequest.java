package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public record EmailPasswordLoginRequest(
    @NotBlank(message = "{validation.email_cannot_be_empty}")
        @NotNull(message = "{validation.email_is_mandatory}")
        String email,
    @NotBlank(message = "{validation.password_cannot_be_empty}")
        @NotNull(message = "{validation.password_is_mandatory}")
        String password) {}
