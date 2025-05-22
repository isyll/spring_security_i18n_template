package com.example.demo.dto.response.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@JsonPropertyOrder({"data", "success", "status", "timestamp", "path"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> extends BaseResponse {

  private T data;

  public ApiResponse(T data, HttpStatus status) {
    super(status);
    this.data = data;
  }

  public ApiResponse(T data) {
    super(HttpStatus.OK);
    this.data = data;
  }

  public ResponseEntity<ApiResponse<T>> build() {
    return new ResponseEntity<>(this, HttpStatus.valueOf(getStatus()));
  }
}
