package com.example.demo.dto.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PaginationParams {
  @Min(value = 1, message = "{validation.page_number_cannot_be_zero}")
  @Schema(
      description = "Page number to retrieve (starting from 1)",
      example = "1",
      minimum = "1",
      defaultValue = "1")
  private int page = 1;

  @Min(value = 1, message = "{validation.page_size_cannot_be_zero}")
  @Max(value = 500, message = "{validation.page_size_cannot_be_more_than_500}")
  @Schema(
      description = "Number of items per page (between 1 and 500)",
      example = "10",
      minimum = "1",
      maximum = "500",
      defaultValue = "25")
  private int size = 25;

  @Schema(
      description =
          "Sorting criteria in the format `field,direction` (e.g. `id,asc` or `name,desc`)",
      example = "id,asc",
      defaultValue = "id,asc")
  @Pattern(regexp = "^\\w+,(asc|desc)$\n", message = "{validation.sort_params_is_invalid}")
  private String sort = "id,asc";

  private Sort getSort() {
    String[] parts = sort.split(",");

    String property = parts[0].trim();
    boolean isDesc = parts.length > 1 && "desc".equalsIgnoreCase(parts[1].trim());

    Sort.Direction direction = isDesc ? Sort.Direction.DESC : Sort.Direction.ASC;

    return Sort.by(direction, property);
  }

  @SuppressWarnings("unused")
  public void setPage(int page) {
    this.page = Math.max(page - 1, 0);
  }

  public Pageable toPageable() {
    return PageRequest.of(page, size, getSort());
  }
}
