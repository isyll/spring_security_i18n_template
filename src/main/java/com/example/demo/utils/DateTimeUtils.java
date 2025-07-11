package com.example.demo.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateTimeUtils {

  public Instant getCurrentTimestamp() {
    return Instant.now();
  }

  public ZonedDateTime convertToSchoolZone(Instant utcInstant, String zoneId) {
    return utcInstant.atZone(ZoneId.of(zoneId));
  }

  public ZonedDateTime nowInSchoolZone(String zoneId) {
    return ZonedDateTime.now(ZoneId.of(zoneId));
  }
}
