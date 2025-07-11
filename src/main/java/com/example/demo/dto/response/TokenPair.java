package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenPair(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("refresh_token") String refreshToken,
    @JsonProperty("must_change_password") Boolean mustChangePassword) {

  public TokenPair(String accessToken, String refreshToken) {
    this(accessToken, refreshToken, false);
  }
}
