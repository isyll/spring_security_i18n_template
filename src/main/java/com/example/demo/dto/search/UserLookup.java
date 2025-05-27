package com.example.demo.dto.search;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLookup(
    @Schema(description = "User ID (UUID or Base62-encoded string)", example = "a2b4cdE93F")
        String id,
    @Schema(description = "User email address", example = "user@example.com") String email,
    @Schema(description = "User phone number", example = "+221770000000") String phone) {}
