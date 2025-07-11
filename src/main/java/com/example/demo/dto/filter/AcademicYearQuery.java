package com.example.demo.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.model.enums.AcademicYearStatus;
import com.example.demo.utils.StringHelper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.springframework.data.domain.Sort;

public record AcademicYearQuery(
    @Schema(
            description = "Name of the academic year (max 9 characters)",
            example = "2024-2025",
            maxLength = 9)
        @Size(max = 9)
        String name,
    @Schema(description = "Start date from", example = "2024-10-01")
        @JsonProperty("start_date_from")
        LocalDate startDateFrom,
    @Schema(description = "Start date to", example = "2024-12-31") @JsonProperty("start_date_to")
        LocalDate startDateTo,
    @Schema(description = "End date from", example = "2025-06-01") @JsonProperty("end_date_from")
        LocalDate endDateFrom,
    @Schema(description = "End date to", example = "2025-07-31") @JsonProperty("end_date_to")
        LocalDate endDateTo,
    @Schema(description = "Status of the academic year", example = "OPENED")
        AcademicYearStatus status,
    @Pattern(regexp = "^\\w+,(asc|desc)$", message = "{validation.sort_params_are_invalid}")
        @Schema(
            description =
                "Sorting criteria in the format `field,direction` (e.g. `id,asc` or `name,desc`)",
            example = "startDate,asc",
            defaultValue = "startDate,asc")
        String sort) {

  public Sort getSort() {
    return StringHelper.parseSort(sort);
  }
}
