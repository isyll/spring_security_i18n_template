package com.example.demo.common.response;

import com.example.demo.utils.DateTimeUtils;
import com.example.demo.utils.RequestUtils;
import java.time.Instant;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
abstract class BaseResponse {

  protected final Instant timestamp;
  protected final String path;
  protected final boolean success;
  protected final int status;

  protected BaseResponse(HttpStatus status) {
    this.timestamp = DateTimeUtils.getCurrentTimestamp();
    this.path = RequestUtils.getCurrentPath();
    this.success = status.is2xxSuccessful();
    this.status = status.value();
  }
}
