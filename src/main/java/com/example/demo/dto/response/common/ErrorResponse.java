package com.example.demo.dto.response.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@JsonPropertyOrder({"message", "success", "status", "timestamp", "path"})
public class ErrorResponse extends BaseResponse {

  private String message;

  public ErrorResponse(HttpStatus status, String message) {
    super(status);
    this.message = message;
  }

  public ResponseEntity<ErrorResponse> build() {
    return new ResponseEntity<>(this, HttpStatus.valueOf(getStatus()));
  }
}
