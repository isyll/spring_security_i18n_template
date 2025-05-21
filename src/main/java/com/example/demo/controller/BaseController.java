package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

abstract class BaseController {

  protected <T> ResponseEntity<ApiResponse<T>> ok(T data) {
    return ApiResponse.success(data);
  }

  protected <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
    return ApiResponse.success(data, message, HttpStatus.OK);
  }

  protected ResponseEntity<ApiResponse<Object>> ok(String message) {
    return ApiResponse.success(message);
  }

  protected <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
    return ApiResponse.success(data, message, HttpStatus.CREATED);
  }

  protected <T> ResponseEntity<ApiResponse<T>> error(String message) {
    return ApiResponse.error(message, HttpStatus.BAD_REQUEST);
  }

  protected <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
    return ApiResponse.error(message, status);
  }

  protected <T> ResponseEntity<ApiResponse<T>> error(String message, Map<String, String> errors) {
    return ApiResponse.error(message, errors);
  }

  protected ResponseEntity<ApiResponse<Object>> error(
      HttpStatus status, Map<String, String> errors) {
    return ApiResponse.error(status, errors);
  }
}
