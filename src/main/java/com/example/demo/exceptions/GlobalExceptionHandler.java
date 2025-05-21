package com.example.demo.exceptions;

import com.example.demo.config.i18n.I18nUtil;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.utils.StringUtils;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @Autowired private I18nUtil i18nUtil;

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getFieldErrors()) {
      String fieldName = StringUtils.camelToSnakeCase(error.getField());
      errors.put(fieldName, error.getDefaultMessage());
    }
    String message = i18nUtil.getMessage("error.validation_errors");
    return ApiResponse.error(message, errors);
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(UniqueConstraintViolationException.class)
  public ResponseEntity<ApiResponse<Object>> handleUniqueConstraintViolation(
      UniqueConstraintViolationException ex) {
    return ApiResponse.error(HttpStatus.CONFLICT, ex.getErrors());
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(InactiveUserActionException.class)
  public ResponseEntity<ApiResponse<Object>> handleInactiveUserActionException(
      InactiveUserActionException ex) {
    return ApiResponse.error(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ApiResponse<Object>> handleAuthorizationDeniedException(
      AuthorizationDeniedException ex) {
    String message = i18nUtil.getMessage("error.forbidden");
    return ApiResponse.error(message, HttpStatus.FORBIDDEN);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(
      ResourceNotFoundException ex) {
    return ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiResponse<Object>> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ex) {
    String message = i18nUtil.getMessage("error.missing_request_parameter");
    return ApiResponse.error(message, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException ex) {
    String message = ex.getMessage();
    if (message == null) {
      message = i18nUtil.getMessage("error.bad_request");
    }
    return ApiResponse.error(message, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResponse<Object>> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex) {
    String message = i18nUtil.getMessage("error.method_not_supported");
    return ApiResponse.error(message, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiResponse<Object>> handleNoResourceFoundException(
      NoResourceFoundException ex) {
    String message = i18nUtil.getMessage("error.no_resource_found");
    return ApiResponse.error(message, HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiResponse<Object>> handleBadCredentialsException(
      BadCredentialsException ex) {
    String message = i18nUtil.getMessage("error.bad_credentials");
    return ApiResponse.error(message, HttpStatus.UNAUTHORIZED);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
    ex.printStackTrace();
    String message = i18nUtil.getMessage("error.an_error_has_occurred");
    return ApiResponse.error(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
