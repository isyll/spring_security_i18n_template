package com.isyll.agrotrade.dto.payload.request;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isyll.agrotrade.models.Gender;
import com.isyll.agrotrade.validators.CountryValidation;
import com.isyll.agrotrade.validators.E164PhoneValidation;
import com.isyll.agrotrade.validators.GenderValidation;

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

    @NotBlank(message = "{validation.username_cannot_be_empty}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{validation.username_is_invalid}")
    @Size(max = 50, message = "{validation.username_is_too_long}")
    @Size(min = 5, message = "{validation.username_is_too_short}")
    private String username;

    @NotBlank(message = "{validation.password_cannot_be_empty}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "{validation.password_is_invalid}")
    @Size(max = 32, message = "{validation.password_is_too_long}")
    private String password;

    @NotBlank(message = "{validation.email_cannot_be_empty}")
    @Size(max = 250, message = "{validation.email_is_too_long}")
    @Email(message = "{validation.email_is_invalid}")
    private String email;

    @NotBlank(message = "{validation.phone_is_mandatory}")
    @Size(max = 50, message = "{validation.phone_is_too_long}")
    @E164PhoneValidation
    private String phone;

    @NotBlank(message = "{validation.phone_cannot_be_empty}")
    @Length(min = 2, max = 2, message = "{validation.country_code_is_invalid}")
    @JsonProperty("country_code")
    @CountryValidation
    private String countryCode;

    @NotNull(message = "{validation.gender_is_mandatory}")
    @GenderValidation
    private Gender gender;

    @NotBlank(message = "{validation.first_name_is_mandatory}")
    @Size(max = 250, message = "{validation.first_name_is_too_long}")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "{validation.last_name_is_mandatory}")
    @Size(max = 250, message = "{validation.last_name_is_too_long}")
    @JsonProperty("last_name")
    private String lastName;
}
