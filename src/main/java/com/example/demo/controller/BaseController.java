package com.example.demo.controller;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.response.ErrorResponse;
import com.example.demo.common.response.PaginatedResponse;
import com.example.demo.common.response.SuccessResponse;
import com.example.demo.common.response.ValidationErrorResponse;
import com.example.demo.context.SchoolContextHolder;
import com.example.demo.dto.filter.UserLookup;
import com.example.demo.model.School;
import com.example.demo.model.User;
import com.example.demo.utils.EntityResolver;
import com.example.demo.utils.RepositoryRegistry;
import com.example.demo.utils.SecurityUtils;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

abstract class BaseController {

  @Autowired private EntityResolver resolver;

  @Autowired private SchoolContextHolder schoolContextHolder;

  @Autowired private RepositoryRegistry repositoryRegistry;

  protected User currentUser() {
    return SecurityUtils.getCurrentUser();
  }

  protected School currentSchool() {
    return schoolContextHolder.getSchool();
  }

  protected <T> T resolve(String base62Id, Class<T> clazz) {
    return resolver.resolveByPublicId(
        repositoryRegistry.getRepositoryEntityType(clazz), base62Id, clazz);
  }

  protected <T> T resolve(UserLookup lookup, Class<T> clazz) {
    return resolver.resolveFromLookup(
        repositoryRegistry.getRepositoryEntityType(clazz), lookup, clazz);
  }

  protected <T> ResponseEntity<ApiResponse<T>> ok(T data) {
    return new ApiResponse<>(data).toResponseEntity();
  }

  protected <T> ResponseEntity<PaginatedResponse<T>> ok(Page<T> data) {
    return new PaginatedResponse<>(data).toResponseEntity();
  }

  protected <T> ResponseEntity<ApiResponse<T>> ok(T data, HttpStatus status) {
    return new ApiResponse<>(data, status).toResponseEntity();
  }

  protected <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
    return new ApiResponse<>(data, message).toResponseEntity();
  }

  protected ResponseEntity<SuccessResponse> ok(String message) {
    return new SuccessResponse(message).toResponseEntity();
  }

  protected ResponseEntity<ErrorResponse> error(String message, HttpStatus status) {
    return new ErrorResponse(status, message).toResponseEntity();
  }

  protected ResponseEntity<ValidationErrorResponse> error(Map<String, String> errors) {
    return new ValidationErrorResponse(errors).toResponseEntity();
  }
}
