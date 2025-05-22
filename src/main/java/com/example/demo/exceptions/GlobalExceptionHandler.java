package com.example.demo.exceptions;

import com.example.demo.config.i18n.I18nUtil;
import com.example.demo.dto.response.common.ErrorResponse;
import com.example.demo.dto.response.common.ValidationErrorResponse;
import com.example.demo.utils.StringUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  private final I18nUtil i18nUtil;

  public GlobalExceptionHandler(I18nUtil i18nUtil) {
    this.i18nUtil = i18nUtil;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    String invalidFieldMessage = i18nUtil.getMessage("error.invalid_field_value");
    Map<String, String> errors =
        ex.getFieldErrors().stream()
            .collect(
                Collectors.toMap(
                    e -> StringUtils.camelToSnakeCase(e.getField()),
                    e -> Optional.ofNullable(e.getDefaultMessage()).orElse(invalidFieldMessage),
                    (a, b) -> a,
                    LinkedHashMap::new));
    return new ValidationErrorResponse(errors).build();
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(UniqueConstraintViolationException.class)
  public ResponseEntity<ValidationErrorResponse> handleUniqueConstraintViolation(
      UniqueConstraintViolationException ex) {
    return new ValidationErrorResponse(HttpStatus.CONFLICT, ex.getErrors()).build();
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(InactiveUserActionException.class)
  public ResponseEntity<ErrorResponse> handleInactiveUserActionException(
      InactiveUserActionException ex) {
    return new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()).build();
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(
      AuthorizationDeniedException ignored) {
    String message = i18nUtil.getMessage("error.forbidden");
    return new ErrorResponse(HttpStatus.FORBIDDEN, message).build();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()).build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ignored) {
    String message = i18nUtil.getMessage("error.missing_request_parameter");
    return new ErrorResponse(HttpStatus.BAD_REQUEST, message).build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
    String message = ex.getMessage();
    if (message == null) {
      message = i18nUtil.getMessage("error.bad_request");
    }
    return new ErrorResponse(HttpStatus.BAD_REQUEST, message).build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ignored) {
    String message = i18nUtil.getMessage("error.method_not_supported");
    return new ErrorResponse(HttpStatus.BAD_REQUEST, message).build();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoResourceFoundException(
      NoResourceFoundException ignored) {
    String message = i18nUtil.getMessage("error.no_resource_found");
    return new ErrorResponse(HttpStatus.NOT_FOUND, message).build();
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(
      BadCredentialsException ignored) {
    String message = i18nUtil.getMessage("error.bad_credentials");
    return new ErrorResponse(HttpStatus.UNAUTHORIZED, message).build();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    log.error("Unhandled exception caught", ex);
    String message = i18nUtil.getMessage("error.an_error_has_occurred");
    return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message).build();
  }
}
