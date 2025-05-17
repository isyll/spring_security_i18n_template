package com.example.demo.dto.response;

import com.example.demo.core.utils.DateTimeUtils;
import com.example.demo.core.utils.RequestUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

  private String message;
  private T data;
  private Map<String, String> errors = null;
  private boolean success;
  private int status;
  private ZonedDateTime timestamp;
  private String path;

  public ApiResponse(String message, T data, HttpStatus httpStatus, Map<String, String> errors) {
    this.message = message;
    this.data = data;
    this.status = httpStatus.value();
    this.success = !httpStatus.isError();
    this.timestamp = DateTimeUtils.getCurrentTimestamp();
    this.errors = errors;
    this.path = RequestUtils.getCurrentPath();
  }

  public static <T> ApiResponse<T> success(T data, String message, HttpStatus status) {
    return new ApiResponse<>(message, data, status, null);
  }

  public static <T> ApiResponse<T> success(T data) {
    return success(data, null, HttpStatus.OK);
  }

  public static <T> ApiResponse<T> success(String message) {
    return success(null, message, HttpStatus.OK);
  }

  public static <T> ApiResponse<T> success(String message, HttpStatus status) {
    return success(null, message, status);
  }

  public static <T> ApiResponse<T> error(String message, HttpStatus status) {
    return new ApiResponse<>(message, null, status, null);
  }

  public static <T> ApiResponse<T> error(String message, Map<String, String> errors) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return new ApiResponse<>(message, null, status, errors);
  }

  public static <T> ApiResponse<T> error(HttpStatus status, Map<String, String> errors) {
    return new ApiResponse<>(null, null, status, errors);
  }

  public ResponseEntity<ApiResponse<T>> toResponseEntity() {
    return ResponseEntity.status(this.status).body(this);
  }
}
