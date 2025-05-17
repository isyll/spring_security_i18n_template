package com.example.demo.dto.request;

import com.example.demo.validators.E164PhoneValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupRequest {

  @NotBlank(message = "{validation.password_cannot_be_empty}")
  @NotNull(message = "{validation.password_is_mandatory}")
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @NotBlank(message = "{validation.email_cannot_be_empty}")
  @NotNull(message = "{validation.email_is_mandatory}")
  private String email;

  @NotBlank(message = "{validation.phone_cannot_be_empty}")
  @NotNull(message = "{validation.phone_is_mandatory}")
  @E164PhoneValidation
  private String phone;

  @JsonProperty("first_name")
  @NotBlank(message = "{validation.first_name_cannot_be_empty}")
  @NotNull(message = "{validation.first_name_is_mandatory}")
  private String firstName;

  @JsonProperty("last_name")
  @NotBlank(message = "{validation.last_name_cannot_be_empty}")
  @NotNull(message = "{validation.last_name_is_mandatory}")
  private String lastName;

  @JsonProperty(value = "photo_url")
  private String photoUrl;
}
