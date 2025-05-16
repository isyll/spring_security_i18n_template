package com.example.demo.dto.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PaginationParams {

  @Min(1)
  private int page = 1;

  @Min(1)
  @Max(500)
  private int size = 10;

  private String sort = "id,asc";

  public void setPage(int page) {
    this.page = Math.max(page - 1, 0);
  }

  public Sort getSort() {
    String[] parts = sort.split(",");
    String property = parts[0];
    Sort.Direction direction =
        parts.length > 1 && parts[1].equalsIgnoreCase("desc")
            ? Sort.Direction.DESC
            : Sort.Direction.ASC;

    return Sort.by(direction, property);
  }
}
