package com.example.demo.dto.school;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.validator.annotations.ValidDateRange;
import java.time.LocalDate;

@ValidDateRange(message = "{validation.start_date_cannot_be_after_end_date}")
public record AcademicYearDto(
    @JsonProperty("start_date") LocalDate startDate,
    @JsonProperty("end_date") LocalDate endDate,
    @JsonProperty("remarks") String remarks) {}
