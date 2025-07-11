package com.example.demo.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"message", "success", "status", "timestamp", "path"})
public class ErrorResponse extends BaseResponse {

  String message;

  public ErrorResponse(HttpStatus status, String message) {
    super(status);
    this.message = message;
  }

  public ResponseEntity<ErrorResponse> toResponseEntity() {
    return new ResponseEntity<>(this, HttpStatus.valueOf(status));
  }
}
