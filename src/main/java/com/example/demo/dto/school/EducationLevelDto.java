package com.example.demo.dto.school;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EducationLevelDto(
    @NotBlank(message = "{validation.label_is_mandatory}")
        @Size(min = 3, max = 50, message = "{validation.label_size}")
        @Pattern(regexp = "^[\\p{L}\\s\\-]+$", message = "{validation.label_invalid_format}")
        String label) {}
