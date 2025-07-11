package com.example.demo.dto.user.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.validator.annotations.E164PhoneValidation;
import com.example.demo.validator.annotations.IsoLanguage;
import com.example.demo.validator.annotations.UniqueEmail;
import com.example.demo.validator.annotations.UniquePhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
public class CreateStudentRequest {

  @JsonProperty("first_name")
  @Size(max = 31, message = "{validation.first_name_max_length}")
  @NotBlank(message = "{validation.first_name_is_mandatory}")
  protected String firstName;

  @JsonProperty("last_name")
  @Size(max = 31, message = "{validation.last_name_max_length}")
  @NotBlank(message = "{validation.last_name_is_mandatory}")
  protected String lastName;

  @Email(message = "{validation.email_is_invalid}")
  @UniqueEmail
  protected String email;

  @E164PhoneValidation
  @UniquePhoneNumber
  @JsonProperty("phone_number")
  protected String phoneNumber;

  @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$", message = "{validation.password_is_invalid}")
  @Size(max = 31, message = "{validation.password_max_length}")
  @NotBlank(message = "{validation.password_is_mandatory}")
  protected String password;

  @JsonProperty("photo_url")
  @URL(message = "{validation.url_is_invalid}")
  @Size(max = 255, message = "{validation.url_max_length}")
  protected String photoUrl;

  @JsonProperty("preferred_locale")
  @IsoLanguage
  protected String preferredLocale;
}
