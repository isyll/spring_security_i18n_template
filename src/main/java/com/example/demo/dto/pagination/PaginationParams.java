package com.example.demo.dto.pagination;

import com.example.demo.utils.StringHelper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@Schema(description = "Pagination parameters")
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
      example = "25",
      minimum = "1",
      maximum = "500",
      defaultValue = "25")
  private int size = 25;

  @Pattern(regexp = "^\\w+,(asc|desc)$", message = "{validation.sort_params_are_invalid}")
  @Schema(
      description =
          "Sorting criteria in the format `field,direction` (e.g. `createdAt,desc` or `name,asc`)",
      example = "createdAt,desc")
  private String sort;

  public Pageable toPageable() {
    return PageRequest.of(Math.max(page - 1, 0), size, StringHelper.parseSort(sort));
  }
}
