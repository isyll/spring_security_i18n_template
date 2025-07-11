package com.example.demo.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLookup(
    @Schema(description = "Public Id", example = "0LjPxBGAz5a") String id,
    @Schema(description = "Registration number", example = "192-85-1506")
        String registrationNumber) {}
