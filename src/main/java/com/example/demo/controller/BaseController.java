package com.example.demo.controller;

import com.example.demo.dto.response.common.ApiResponse;
import com.example.demo.dto.response.common.ErrorResponse;
import com.example.demo.dto.response.common.PaginatedResponse;
import com.example.demo.dto.response.common.SuccessResponse;
import com.example.demo.dto.response.common.ValidationErrorResponse;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

abstract class BaseController {
  protected static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
    return new ApiResponse<>(data).build();
  }

  protected static <T> ResponseEntity<PaginatedResponse<T>> ok(Page<T> data) {
    return new PaginatedResponse<>(data).build();
  }

  protected static <T> ResponseEntity<ApiResponse<T>> ok(T data, HttpStatus status) {
    return new ApiResponse<>(data, status).build();
  }

  protected static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
    return new ApiResponse<>(data, message).build();
  }

  protected static ResponseEntity<SuccessResponse> ok(String message) {
    return new SuccessResponse(message).build();
  }

  protected static ResponseEntity<ErrorResponse> error(String message, HttpStatus status) {
    return new ErrorResponse(status, message).build();
  }

  protected static ResponseEntity<ValidationErrorResponse> error(Map<String, String> errors) {
    return new ValidationErrorResponse(errors).build();
  }
}
