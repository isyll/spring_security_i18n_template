package com.example.demo.dto.response.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"message", "data", "success", "status", "timestamp", "path"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> extends BaseResponse {
  String message;
  T data;

  public ApiResponse(T data, String message) {
    super(HttpStatus.OK);
    this.data = data;
    this.message = message;
  }

  public ApiResponse(T data, HttpStatus status) {
    super(status);
    this.data = data;
    this.message = null;
  }

  public ApiResponse(T data) {
    super(HttpStatus.OK);
    this.data = data;
    this.message = null;
  }
}
