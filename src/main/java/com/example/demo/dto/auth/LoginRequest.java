package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "{validation.identifier_is_mandatory}") String identifier,
    @NotBlank(message = "{validation.password_is_mandatory}") String password) {}
