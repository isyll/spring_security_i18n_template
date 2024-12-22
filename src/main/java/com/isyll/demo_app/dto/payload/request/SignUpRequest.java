package com.isyll.demo_app.dto.payload.request;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isyll.demo_app.models.Gender;
import com.isyll.demo_app.validators.CountryValidation;
import com.isyll.demo_app.validators.E164PhoneValidation;
import com.isyll.demo_app.validators.GenderValidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpRequest {

    @NotBlank(message = "{validation.username_cannot_be_empty}")
    @NotNull(message = "{validation.username_is_mandatory}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{validation.username_is_invalid}")
    @Size(max = 50, message = "{validation.username_is_too_long}")
    @Size(min = 5, message = "{validation.username_is_too_short}")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "{validation.password_is_invalid}")
    @NotBlank(message = "{validation.password_cannot_be_empty}")
    @NotNull(message = "{validation.password_is_mandatory}")
    @Size(max = 32, message = "{validation.password_is_too_long}")
    private String password;

    @NotBlank(message = "{validation.email_cannot_be_empty}")
    @NotNull(message = "{validation.email_is_mandatory}")
    @Size(max = 250, message = "{validation.email_is_too_long}")
    @Email(message = "{validation.email_is_invalid}")
    private String email;

    @NotBlank(message = "{validation.phone_cannot_be_empty}")
    @NotNull(message = "{validation.phone_is_mandatory}")
    @Size(max = 50, message = "{validation.phone_is_too_long}")
    @E164PhoneValidation
    private String phone;

    @NotBlank(message = "{validation.country_code_cannot_be_empty}")
    @NotNull(message = "{validation.country_code_is_mandatory}")
    @Length(min = 2, max = 2, message = "{validation.country_code_is_invalid}")
    @JsonProperty("country_code")
    @CountryValidation
    private String countryCode;

    @NotNull(message = "{validation.gender_is_mandatory}")
    @GenderValidation
    private Gender gender;

    @NotBlank(message = "{validation.first_name_is_mandatory}")
    @NotNull(message = "{validation.first_name_is_mandatory}")
    @Size(max = 250, message = "{validation.first_name_is_too_long}")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "{validation.last_name_is_mandatory}")
    @NotNull(message = "{validation.last_name_is_mandatory}")
    @Size(max = 250, message = "{validation.last_name_is_too_long}")
    @JsonProperty("last_name")
    private String lastName;

}
