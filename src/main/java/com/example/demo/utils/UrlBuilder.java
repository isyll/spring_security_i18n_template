package com.example.demo.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

public final class UrlBuilder {

  private final String baseUrl;

  public UrlBuilder(String baseUrl) {
    this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
  }

  public String build(String path, Map<String, String> queryParams) {
    StringBuilder url = new StringBuilder();
    url.append(baseUrl);
    if (!path.startsWith("/")) {
      url.append("/");
    }
    url.append(path);

    if (queryParams != null && !queryParams.isEmpty()) {
      url.append("?");
      StringJoiner joiner = new StringJoiner("&");
      for (Map.Entry<String, String> entry : queryParams.entrySet()) {
        joiner.add(encode(entry.getKey()) + "=" + encode(entry.getValue()));
      }
      url.append(joiner);
    }

    return url.toString();
  }

  private String encode(String input) {
    return URLEncoder.encode(input, StandardCharsets.UTF_8);
  }
}
