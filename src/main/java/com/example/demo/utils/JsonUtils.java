package com.example.demo.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {

  public Map<String, String> getJsonFieldNames(Class<?> clazz) {
    Map<String, String> map = new HashMap<>();
    for (Field field : clazz.getDeclaredFields()) {
      JsonProperty annotation = field.getAnnotation(JsonProperty.class);
      if (annotation != null && !annotation.value().isEmpty()) {
        map.put(field.getName(), annotation.value());
      }
    }
    return map;
  }
}
