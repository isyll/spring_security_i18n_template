package com.example.demo.core.utils;

public class StringUtils {

  public static String camelToSnakeCase(String camelCaseString) {
    if (camelCaseString == null || camelCaseString.isEmpty()) {
      return camelCaseString;
    }

    return camelCaseString.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
  }

  public static int tryParseInt(String str, int defaultValue) {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }
}
