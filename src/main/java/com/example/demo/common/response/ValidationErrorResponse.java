package com.example.demo.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"message", "errors", "success", "status", "timestamp", "path"})
@JsonInclude(Include.NON_NULL)
public class ValidationErrorResponse extends BaseResponse {

  Map<String, String> errors;
  String message;

  public ValidationErrorResponse(String message) {
    super(HttpStatus.BAD_REQUEST);
    this.errors = null;
    this.message = message;
  }

  public ValidationErrorResponse(Map<String, String> errors) {
    super(HttpStatus.BAD_REQUEST);
    this.errors = errors;
    this.message = null;
  }

  public ValidationErrorResponse(HttpStatus status, Map<String, String> errors) {
    super(status);
    this.errors = errors;
    this.message = null;
  }

  public ResponseEntity<ValidationErrorResponse> toResponseEntity() {
    return new ResponseEntity<>(this, HttpStatus.valueOf(status));
  }
}
