package com.example.demo.dto.response.common;

import com.example.demo.utils.RequestUtils;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
abstract class BaseResponse {

  private ZonedDateTime timestamp;
  private String path;
  private boolean success;
  private int status;

  public BaseResponse(HttpStatus status) {
    this.timestamp = ZonedDateTime.now();
    this.path = RequestUtils.getCurrentPath();
    this.success = status.is2xxSuccessful();
    this.status = status.value();
  }
}
