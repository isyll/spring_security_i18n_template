package com.example.demo.utils;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnvUtils {

  private final Dotenv dotenv = Dotenv.load();

  private String get(String key) {
    return dotenv.get(key);
  }

  public String get(String key, String defaultValue) {
    return Optional.ofNullable(dotenv.get(key)).orElse(defaultValue);
  }

  public int getInt(String key, int defaultValue) {
    return StringHelper.tryParseInt(get(key), defaultValue);
  }

  public long getLong(String key, long defaultValue) {
    return StringHelper.tryParseLong(get(key), defaultValue);
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    return StringHelper.tryParseBoolean(get(key), defaultValue);
  }
}
