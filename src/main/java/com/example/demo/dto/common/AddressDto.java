package com.example.demo.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.validator.annotations.CountryValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressDto(
    @NotBlank(message = "{validation.street_is_mandatory}")
        @Size(max = 100, message = "{validation.address_street_max}")
        String street,
    @NotBlank(message = "{validation.city_is_mandatory}")
        @Size(max = 50, message = "{validation.address_city_max}")
        String city,
    @NotBlank(message = "{validation.country_is_mandatory}")
        @Size(max = 100, message = "{validation.address_country_max}")
        @CountryValidation
        String country,
    @NotBlank(message = "{validation.postal_code_is_mandatory}")
        @Size(max = 12, message = "{validation.address_postal_code_max}")
        @JsonProperty("postal_code")
        String postalCode) {}
