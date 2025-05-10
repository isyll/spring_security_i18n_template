package com.example.demo.core.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.stream.Stream;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PaginationResponse<T> {

  private Stream<T> content;

  @JsonProperty("total_elements")
  private long totalElements;

  @JsonProperty("total_pages")
  private long totalPages;

  @JsonProperty("has_next")
  private boolean hasNext;

  @JsonProperty("page_number")
  private long pageNumber;

  @JsonProperty("page_size")
  private long pageSize;

  public PaginationResponse(Page<T> data) {
    this.content = data.get();
    this.totalElements = data.getTotalElements();
    this.totalPages = data.getTotalPages();
    this.hasNext = data.hasNext();
    this.pageNumber = data.getNumber();
    this.pageSize = data.getSize();
  }
}
