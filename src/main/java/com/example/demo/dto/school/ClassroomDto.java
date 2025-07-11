package com.example.demo.dto.school;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClassroomDto(
    @NotBlank(message = "{validation.classroom_name_is_mandatory}")
        @Size(max = 31, message = "{validation.classroom_name_max_length}")
        @Size(min = 3, message = "{validation.classroom_name_min_length}")
        String name,
    @Size(max = 1000, message = "{validation.classroom_description_max_length}") String description,
    @NotNull(message = "{validation.classroom_capacity_is_mandatory}")
        @Min(value = 1, message = "{validation.classroom_capacity_must_be_positive}")
        @Max(value = 99_999, message = "{validation.classroom_capacity_max_value}")
        Integer capacity) {}
