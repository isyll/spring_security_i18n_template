package com.example.demo.dto.response.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@JsonPropertyOrder({"message", "success", "status", "timestamp", "path"})
public class SuccessResponse extends BaseResponse {

  private String message;

  public SuccessResponse(String message) {
    super(HttpStatus.OK);
    this.message = message;
  }

  public ResponseEntity<SuccessResponse> build() {
    return new ResponseEntity<>(this, HttpStatus.valueOf(getStatus()));
  }
}
