package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PaginationResponse<T> {

  private List<T> content;

  @JsonProperty("total_elements")
  private long totalElements;

  @JsonProperty("total_pages")
  private int totalPages;

  @JsonProperty("current_page")
  private int currentPage;

  private int size;

  public PaginationResponse(Page<T> data) {
    this.content = data.getContent();
    this.totalElements = data.getTotalElements();
    this.totalPages = data.getTotalPages();
    this.currentPage = data.getNumber();
    this.size = this.content.size();
  }
}
