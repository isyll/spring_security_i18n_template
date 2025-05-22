package com.example.demo.dto.response.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@JsonPropertyOrder({"errors", "success", "status", "timestamp", "path"})
public class ValidationErrorResponse extends BaseResponse {

  private Map<String, String> errors;

  public ValidationErrorResponse(Map<String, String> errors) {
    super(HttpStatus.BAD_REQUEST);
    this.errors = errors;
  }

  public ValidationErrorResponse(HttpStatus status, Map<String, String> errors) {
    super(status);
    this.errors = errors;
  }

  public ResponseEntity<ValidationErrorResponse> build() {
    return new ResponseEntity<>(this, HttpStatus.valueOf(getStatus()));
  }
}
