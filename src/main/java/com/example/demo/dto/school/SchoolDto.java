package com.example.demo.dto.school;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.dto.common.AddressDto;
import com.example.demo.validator.annotations.E164PhoneValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;

public record SchoolDto(
    @NotBlank(message = "{validation.school_name_is_mandatory}") String name,
    @Pattern(
            regexp = "^[a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?$",
            message = "{validation.invalid_school_code_format}")
        String code,
    @Valid AddressDto address,
    @Email(message = "{validation.email_is_invalid}")
        @NotBlank(message = "{validation.email_is_mandatory}")
        String email,
    @NotBlank(message = "{validation.phone_number_is_mandatory}")
        @E164PhoneValidation
        @JsonProperty("phone_number")
        String phoneNumber,
    @JsonProperty("logo_url") @URL(message = "{validation.url_is_invalid}") String logoUrl) {}
