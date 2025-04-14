package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SigninRequest {

  @NotBlank(message = "{validation.email_cannot_be_empty}")
  @NotNull(message = "{validation.email_is_mandatory}")
  private String email;

  @NotBlank(message = "{validation.password_cannot_be_empty}")
  @NotNull(message = "{validation.password_is_mandatory}")
  private String password;
}
