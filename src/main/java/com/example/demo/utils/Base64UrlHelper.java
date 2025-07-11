package com.example.demo.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Base64UrlHelper {

  public String encodeString(String input) {
    byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
  }

  public String decodeToString(String base64Url) {
    byte[] decoded = Base64.getUrlDecoder().decode(base64Url);
    return new String(decoded, StandardCharsets.UTF_8);
  }

  public String encodeBytes(byte[] bytes) {
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
  }

  public byte[] decodeToBytes(String base64Url) {
    return Base64.getUrlDecoder().decode(base64Url);
  }

  public <T> String encodeObject(T obj) {
    if (obj == null) {
      throw new IllegalArgumentException("Object cannot be null");
    }
    String str = obj.toString();
    return encodeString(str);
  }

  public <T> T decodeToObject(String base64Url, Function<String, T> parser) {
    if (base64Url == null || parser == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    String decodedStr = decodeToString(base64Url);
    return parser.apply(decodedStr);
  }
}
