package com.example.demo.core.utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateTimeUtils {

  public static ZonedDateTime getCurrentTimestamp() {
    return ZonedDateTime.now(ZoneOffset.UTC);
  }
}
