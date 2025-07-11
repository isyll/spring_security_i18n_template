package com.example.demo.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberLookup(
    @Schema(description = "Public Id", example = "0LjPxBGAz5a") String id,
    @Schema(description = "Registration number", example = "192-85-1506")
        @JsonProperty("registration_number")
        String registrationNumber) {}
