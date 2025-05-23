package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenRequest {
  @NotNull(message = "{validation.refresh_token_is_mandatory}")
  @NotBlank(message = "{validation.refresh_token_cannot_be_empty}")
  @JsonProperty("refresh_token")
  private String refreshToken;
}
