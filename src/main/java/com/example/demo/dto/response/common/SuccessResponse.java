package com.example.demo.dto.response.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"message", "success", "status", "timestamp", "path"})
public class SuccessResponse extends BaseResponse {

  String message;

  public SuccessResponse(String message) {
    super(HttpStatus.OK);
    this.message = message;
  }

  public ResponseEntity<SuccessResponse> build() {
    return new ResponseEntity<>(this, HttpStatus.valueOf(getStatus()));
  }
}
