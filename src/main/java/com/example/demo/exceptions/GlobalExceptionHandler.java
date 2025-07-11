package com.example.demo.exceptions;

import com.example.demo.common.response.ErrorResponse;
import com.example.demo.common.response.ValidationErrorResponse;
import com.example.demo.utils.EmailHelper;
import com.example.demo.utils.JsonUtils;
import com.example.demo.utils.RequestUtils;
import com.example.demo.utils.StringHelper;
import com.example.demo.utils.Translator;
import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final Translator translator;
  private final EmailHelper emailHelper;

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleException(MethodArgumentTypeMismatchException ex) {
    String fieldName = ex.getName();
    String invalidValue = ex.getValue() != null ? ex.getValue().toString() : "null";

    String expectedValues = translator.t("message.unknown");
    if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
      Object[] enumConstants = ex.getRequiredType().getEnumConstants();
      expectedValues =
          Arrays.stream(enumConstants).map(Object::toString).collect(Collectors.joining(", "));
    }

    String message =
        translator.t("error.invalid_enum", new Object[] {invalidValue, fieldName, expectedValues});

    return new ErrorResponse(HttpStatus.BAD_REQUEST, message).toResponseEntity();
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleException(BindException ex) {
    var fieldError = ex.getBindingResult().getFieldErrors().stream().findFirst();

    String fieldName =
        fieldError.map(error -> StringHelper.camelToSnakeCase(error.getField())).orElse("unknown");

    String invalidValue =
        fieldError.map(error -> String.valueOf(error.getRejectedValue())).orElse("null");

    String message =
        translator.t("error.invalid_field_format", new Object[] {invalidValue, fieldName});

    return new ErrorResponse(HttpStatus.BAD_REQUEST, message).toResponseEntity();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ValidationErrorResponse> handleException(
      MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();

    List<ObjectError> globalErrors = bindingResult.getGlobalErrors();
    if (!globalErrors.isEmpty()) {
      return new ValidationErrorResponse(globalErrors.getFirst().getDefaultMessage())
          .toResponseEntity();
    }

    Object target = bindingResult.getTarget();
    Map<String, String> jsonNames =
        (target != null) ? JsonUtils.getJsonFieldNames(target.getClass()) : Map.of();
    Map<String, String> errors = new LinkedHashMap<>();

    for (FieldError error : bindingResult.getFieldErrors()) {
      String field = error.getField();
      String jsonField = jsonNames.getOrDefault(field, StringHelper.camelToSnakeCase(field));
      String rejected =
          error.getRejectedValue() != null ? error.getRejectedValue().toString() : "null";

      String message;
      if (StringUtils.hasText(error.getDefaultMessage())) {
        message = error.getDefaultMessage();
      } else {
        message =
            rejected.equals("null") || rejected.isEmpty()
                ? translator.t("error.required_field")
                : translator.t("error.invalid_field_format", new Object[] {rejected, jsonField});
      }

      errors.putIfAbsent(jsonField, message);
    }

    if (errors.isEmpty()) {
      return new ValidationErrorResponse(translator.t("error.data_not_valid")).toResponseEntity();
    }

    return new ValidationErrorResponse(errors).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<ErrorResponse> handleException(MaxUploadSizeExceededException ex) {
    String message;

    if (ex.getCause() != null && ex.getCause().getMessage().contains("auth")) {
      message = translator.t("error.upload_request_too_large");
    } else {
      message = translator.t("error.upload_file_too_large");
    }

    return new ErrorResponse(HttpStatus.BAD_REQUEST, message).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(UniqueConstraintViolationException.class)
  public ResponseEntity<ValidationErrorResponse> handleException(
      UniqueConstraintViolationException ex) {
    return new ValidationErrorResponse(HttpStatus.CONFLICT, ex.getErrors()).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ErrorResponse> handleException(AuthorizationDeniedException ignored) {
    String message =
        RequestUtils.isOperationRequest()
            ? translator.t("error.forbidden_operation")
            : translator.t("error.forbidden");
    return new ErrorResponse(HttpStatus.FORBIDDEN, message).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(ForbiddenOperationException.class)
  public ResponseEntity<ErrorResponse> handleException(ForbiddenOperationException ignored) {
    String message = translator.t("error.forbidden_operation");
    return new ErrorResponse(HttpStatus.UNAUTHORIZED, message).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(NoHandlerFoundException ignored) {
    String message = translator.t("error.page_not_found");
    return new ErrorResponse(HttpStatus.NOT_FOUND, message).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(NoResourceFoundException ignored) {
    String message = translator.t("error.page_not_found");
    return new ErrorResponse(HttpStatus.NOT_FOUND, message).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleException(
      MissingServletRequestParameterException ignored) {
    String message = translator.t("error.missing_request_parameter");
    return new ErrorResponse(HttpStatus.BAD_REQUEST, message).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException ignored) {
    String message = translator.t("error.invalid_data_format");
    return new ErrorResponse(HttpStatus.BAD_REQUEST, message).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleException(BadRequestException ex) {
    String message = ex.getMessage() != null ? ex.getMessage() : translator.t("error.bad_request");
    return new ErrorResponse(HttpStatus.BAD_REQUEST, message).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleException(
      HttpRequestMethodNotSupportedException ignored) {
    String message = translator.t("error.method_not_supported");
    return new ErrorResponse(HttpStatus.BAD_REQUEST, message).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleException(BadCredentialsException ignored) {
    String message = translator.t("error.bad_credentials");
    return new ErrorResponse(HttpStatus.UNAUTHORIZED, message).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(SchoolOwnershipException.class)
  public ResponseEntity<ErrorResponse> handleException(SchoolOwnershipException ignored) {
    String message = translator.t("error.school.unauthorized");
    return new ErrorResponse(HttpStatus.UNAUTHORIZED, message).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleException(UnauthorizedException ex) {
    return new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()).toResponseEntity();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    log.error("Unhandled exception caught", ex);
    emailHelper.sendUncaughtExceptionEmail(ex);
    String message = translator.t("error.an_error_has_occurred");
    return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message).toResponseEntity();
  }
}
