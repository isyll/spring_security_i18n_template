package com.example.demo.utils;

import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@UtilityClass
public class StringHelper {

  public String camelToSnakeCase(String camelCaseString) {
    if (camelCaseString == null || camelCaseString.isEmpty()) {
      return camelCaseString;
    }

    return camelCaseString.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
  }

  public Sort parseSort(String sortString) {
    if (!StringUtils.hasText(sortString)) {
      return Sort.unsorted();
    }
    String[] parts = sortString.split(",");
    String property = parts[0].trim();
    boolean isDesc = parts.length > 1 && "desc".equalsIgnoreCase(parts[1].trim());
    return Sort.by(isDesc ? Sort.Direction.DESC : Sort.Direction.ASC, property);
  }

  public int tryParseInt(String str, int defaultValue) {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public long tryParseLong(String str, long defaultValue) {
    try {
      return Long.parseLong(str);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public boolean tryParseBoolean(String str, boolean defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    String normalized = str.trim().toLowerCase();
    return switch (normalized) {
      case "true" -> true;
      case "false" -> false;
      default -> defaultValue;
    };
  }

  public boolean isValidEmail(String email) {
    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    return email != null && email.matches(regex);
  }

  public boolean isNumeric(String str) {
    return str != null && str.matches("\\d+");
  }

  public boolean isValidUUID(String input) {
    if (input == null || input.isBlank()) {
      return false;
    }
    try {
      UUID.fromString(input);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
