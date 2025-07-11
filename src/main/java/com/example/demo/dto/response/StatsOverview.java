package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Aggregated statistics about the school")
public record StatsOverview(
    @Schema(description = "Total number of students enrolled") @JsonProperty("total_students")
        long totalStudents,
    @Schema(description = "Total number of teachers assigned") @JsonProperty("total_teachers")
        long totalTeachers,
    @Schema(description = "Total number of classes created") @JsonProperty("total_classes")
        long totalClasses,
    @Schema(description = "Current academic year") @JsonProperty("current_academic_year")
        String currentAcademicYear) {}
