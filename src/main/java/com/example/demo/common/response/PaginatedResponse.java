package com.example.demo.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({
  "success",
  "data",
  "total_elements",
  "total_pages",
  "current_page",
  "size",
  "status",
  "timestamp",
  "path"
})
public class PaginatedResponse<T> extends BaseResponse {

  List<T> data;

  private record Metadata<T>(
      @JsonProperty("total_elements") long totalElements,
      @JsonProperty("total_pages") int totalPages,
      @JsonProperty("current_page") int currentPage,
      int size,
      @JsonProperty("page_count") int pageCount,
      @JsonProperty("is_first") boolean isFirst,
      @JsonProperty("is_last") boolean isLast,
      @JsonProperty("has_next") boolean hasNext,
      @JsonProperty("has_previous") boolean hasPrevious) {
    public Metadata(Page<T> page) {
      this(
          page.getTotalElements(),
          page.getTotalPages(),
          page.getNumber(),
          page.getSize(),
          page.getNumberOfElements(),
          page.isFirst(),
          page.isLast(),
          page.hasNext(),
          page.hasPrevious());
    }
  }

  Metadata<T> meta;

  public PaginatedResponse(Page<T> page) {
    super(HttpStatus.OK);
    this.data = page.getContent();
    this.meta = new Metadata<>(page);
  }

  public ResponseEntity<PaginatedResponse<T>> toResponseEntity() {
    return new ResponseEntity<>(this, HttpStatus.valueOf(status));
  }
}
