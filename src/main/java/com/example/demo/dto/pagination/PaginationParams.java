package com.example.demo.dto.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PaginationParams {

  @Min(1)
  @Schema(
      description = "Page number to retrieve (starting from 1)",
      example = "1",
      minimum = "1",
      defaultValue = "1")
  private int page = 1;

  @Min(1)
  @Max(500)
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
  private String sort = "id,asc";

  private Sort getSort() {
    String[] parts = sort.split(",");
    String property = parts[0];
    Sort.Direction direction =
        parts.length > 1 && parts[1].equalsIgnoreCase("desc")
            ? Sort.Direction.DESC
            : Sort.Direction.ASC;

    return Sort.by(direction, property);
  }

  public void setPage(int page) {
    this.page = Math.max(page - 1, 0);
  }

  public Pageable getPageable() {
    return PageRequest.of(page, size, getSort());
  }
}
