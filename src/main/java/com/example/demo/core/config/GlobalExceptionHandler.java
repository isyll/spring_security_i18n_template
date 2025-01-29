package com.example.demo.core.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.demo.core.exceptions.BadRefreshTokenException;
import com.example.demo.core.exceptions.BadRequestException;
import com.example.demo.core.exceptions.InactiveUserActionException;
import com.example.demo.core.exceptions.InvalidTokenException;
import com.example.demo.core.exceptions.PaginationParamsException;
import com.example.demo.core.exceptions.ResourceNotFoundException;
import com.example.demo.core.exceptions.UniqueConstraintViolationException;
import com.example.demo.core.payload.ApiResponse;
import com.example.demo.core.utils.StringUtils;
import com.example.demo.i18n.I18nUtil;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    I18nUtil i18nUtil;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
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
    public ApiResponse<Object> handleUniqueConstraintViolation(UniqueConstraintViolationException ex) {
        return ApiResponse.error(HttpStatus.CONFLICT, ex.getErrors());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InactiveUserActionException.class)
    public ApiResponse<Object> handleInactiveUserActionException(InactiveUserActionException ex) {
        return ApiResponse.error(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ApiResponse<Object> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        String message = i18nUtil.getMessage("error.forbidden");
        return ApiResponse.error(message, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PaginationParamsException.class)
    public ApiResponse<Object> handlePaginationParamsException(PaginationParamsException ex) {
        String message = i18nUtil.getMessage("pagination.parameters_are_invalid");
        return ApiResponse.error(message, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse<Object> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex) {
        String message = i18nUtil.getMessage("error.missing_request_parameter");
        return ApiResponse.error(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ApiResponse<Object> handleBadRequestException(BadRequestException ex) {
        String message = ex.getMessage();
        if (message == null) {
            message = i18nUtil.getMessage("error.bad_request");
        }
        return ApiResponse.error(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<Object> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        String message = i18nUtil.getMessage("error.method_not_supported");
        return ApiResponse.error(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoResourceFoundException.class)
    public ApiResponse<Object> handleNoResourceFoundException(
            NoResourceFoundException ex) {
        String message = i18nUtil.getMessage("error.no_esource_found");
        return ApiResponse.error(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTokenException.class)
    public ApiResponse<Object> handleInvalidTokenException(InvalidTokenException ex) {
        String message = i18nUtil.getMessage("error.invalid_token");
        return ApiResponse.error(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRefreshTokenException.class)
    public ApiResponse<Object> handleBadRefreshTokenException(BadRefreshTokenException ex) {
        String message = i18nUtil.getMessage("error.bad_refresh_token");
        return ApiResponse.error(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponse<Object> handleBadCredentialsException(BadCredentialsException ex) {
        String message = i18nUtil.getMessage("error.bad_credentials");
        return ApiResponse.error(message, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleException(Exception ex) {
        ex.printStackTrace();
        String message = i18nUtil.getMessage("error.an_error_has_occurred");
        return ApiResponse.error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
