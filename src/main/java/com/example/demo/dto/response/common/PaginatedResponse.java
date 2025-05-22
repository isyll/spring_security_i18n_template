package com.example.demo.dto.response.common;

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
  "content",
  "total_elements",
  "total_pages",
  "current_page",
  "size",
  "success",
  "status",
  "timestamp",
  "path"
})
public class PaginatedResponse<T> extends BaseResponse {

  List<T> content;

  @JsonProperty("total_elements")
  long totalElements;

  @JsonProperty("total_pages")
  int totalPages;

  @JsonProperty("current_page")
  int currentPage;

  int size;

  public PaginatedResponse(Page<T> data) {
    super(HttpStatus.OK);
    this.content = data.getContent();
    this.totalElements = data.getTotalElements();
    this.totalPages = data.getTotalPages();
    this.currentPage = data.getNumber() + 1;
    this.size = this.content.size();
  }

  public ResponseEntity<PaginatedResponse<T>> build() {
    return new ResponseEntity<>(this, HttpStatus.valueOf(getStatus()));
  }
}
