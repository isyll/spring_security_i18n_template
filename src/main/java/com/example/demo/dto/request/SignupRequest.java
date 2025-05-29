package com.example.demo.dto.request;

import com.example.demo.validator.annotations.E164PhoneValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequest(
    @JsonProperty(access = Access.WRITE_ONLY)
        @NotBlank(message = "{validation.password_cannot_be_empty}")
        @NotNull(message = "{validation.password_is_mandatory}")
        String password,
    @NotBlank(message = "{validation.email_cannot_be_empty}")
        @NotNull(message = "{validation.email_is_mandatory}")
        String email,
    @E164PhoneValidation
        @NotBlank(message = "{validation.phone_cannot_be_empty}")
        @NotNull(message = "{validation.phone_is_mandatory}")
        String phone,
    @JsonProperty("first_name")
        @NotBlank(message = "{validation.first_name_cannot_be_empty}")
        @NotNull(message = "{validation.first_name_is_mandatory}")
        String firstName,
    @JsonProperty("last_name")
        @NotBlank(message = "{validation.last_name_cannot_be_empty}")
        @NotNull(message = "{validation.last_name_is_mandatory}")
        String lastName,
    @JsonProperty(value = "photo_url") String photoUrl) {}
