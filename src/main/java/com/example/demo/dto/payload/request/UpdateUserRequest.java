package com.example.demo.dto.payload.request;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.models.Gender;
import com.example.demo.validators.CountryValidation;
import com.example.demo.validators.E164PhoneValidation;
import com.example.demo.validators.GenderValidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "{validation.user.username_cannot_be_empty}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{validation.user.username_is_invalid}")
    @Size(max = 50, message = "{validation.user.username_is_too_long}")
    @Size(min = 5, message = "{validation.user.username_is_too_short}")
    private String username;

    @NotBlank(message = "{validation.user.password_cannot_be_empty}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "{validation.user.password_is_invalid}")
    @Size(max = 32, message = "{validation.user.password_is_too_long}")
    private String password;

    @NotBlank(message = "{validation.user.email_cannot_be_empty}")
    @Size(max = 250, message = "{validation.user.email_is_too_long}")
    @Email(message = "{validation.user.email_is_invalid}")
    private String email;

    @NotBlank(message = "{validation.user.phone_is_mandatory}")
    @Size(max = 50, message = "{validation.user.phone_is_too_long}")
    @E164PhoneValidation
    private String phone;

    @NotBlank(message = "{validation.user.phone_cannot_be_empty}")
    @Length(min = 2, max = 2, message = "{validation.user.country_code_is_invalid}")
    @JsonProperty("country_code")
    @CountryValidation
    private String countryCode;

    @NotNull(message = "{validation.user.gender_is_mandatory}")
    @GenderValidation
    private Gender gender;

    @NotBlank(message = "{validation.user.first_name_is_mandatory}")
    @Size(max = 250, message = "{validation.user.first_name_is_too_long}")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "{validation.user.last_name_is_mandatory}")
    @Size(max = 250, message = "{validation.user.last_name_is_too_long}")
    @JsonProperty("last_name")
    private String lastName;
}
