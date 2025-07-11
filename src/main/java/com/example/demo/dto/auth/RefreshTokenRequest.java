package com.example.demo.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
    @JsonProperty("refresh_token") @NotBlank(message = "{validation.refresh_token_cannot_be_empty}")
        String refreshToken) {}
