package com.example.demo.dto.response.common;

import com.example.demo.utils.RequestUtils;
import java.time.ZonedDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
abstract class BaseResponse {
  private final ZonedDateTime timestamp;
  private final String path;
  private final boolean success;
  private final int status;

  public BaseResponse(HttpStatus status) {
    this.timestamp = ZonedDateTime.now();
    this.path = RequestUtils.getCurrentPath();
    this.success = status.is2xxSuccessful();
    this.status = status.value();
  }

  @SuppressWarnings("unchecked")
  public <T extends BaseResponse> ResponseEntity<T> build() {
    return new ResponseEntity<>((T) this, HttpStatus.valueOf(status));
  }
}
