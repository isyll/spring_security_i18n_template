package com.example.demo.dto.response;

import com.example.demo.utils.DateTimeUtils;
import com.example.demo.utils.RequestUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

  private String message;
  private T data;
  private Map<String, String> errors;
  private boolean success;
  private int status;
  private ZonedDateTime timestamp;
  private String path;

  private ApiResponse(String message, T data, HttpStatus httpStatus, Map<String, String> errors) {
    this.message = message;
    this.data = data;
    this.errors = errors;
    this.status = httpStatus.value();
    this.success = !httpStatus.isError();
    this.timestamp = DateTimeUtils.getCurrentTimestamp();
    this.path = RequestUtils.getCurrentPath();
  }

  public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
    return build(data, null, HttpStatus.OK, null);
  }

  public static <T> ResponseEntity<ApiResponse<T>> success(String message) {
    return build(null, message, HttpStatus.OK, null);
  }

  public static <T> ResponseEntity<ApiResponse<T>> success(
      T data, String message, HttpStatus status) {
    return build(data, message, status, null);
  }

  public static <T> ResponseEntity<ApiResponse<T>> error(String message) {
    return build(null, message, HttpStatus.BAD_REQUEST, null);
  }

  public static <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
    return build(null, message, status, null);
  }

  public static <T> ResponseEntity<ApiResponse<T>> error(
      String message, Map<String, String> errors) {
    return build(null, message, HttpStatus.BAD_REQUEST, errors);
  }

  public static <T> ResponseEntity<ApiResponse<T>> error(
      HttpStatus status, Map<String, String> errors) {
    return build(null, null, status, errors);
  }

  public static <T> ResponseEntity<ApiResponse<T>> error(
      T data, String message, HttpStatus status, Map<String, String> errors) {
    return build(data, message, status, errors);
  }

  private static <T> ResponseEntity<ApiResponse<T>> build(
      T data, String message, HttpStatus status, Map<String, String> errors) {
    return new ApiResponse<>(message, data, status, errors).toResponseEntity();
  }

  private ResponseEntity<ApiResponse<T>> toResponseEntity() {
    return ResponseEntity.status(this.status).body(this);
  }
}
