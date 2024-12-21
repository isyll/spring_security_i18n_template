package com.isyll.demo_app.validators;

import com.isyll.demo_app.utils.IsoUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryValidator implements ConstraintValidator<CountryValidation, String> {

    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        if (value == null) {
            return true;
        }

        return IsoUtils.isValidISOCountry(value);
    }
}
