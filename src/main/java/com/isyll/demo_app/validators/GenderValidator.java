package com.isyll.agrotrade.validators;

import java.util.Arrays;

import com.isyll.agrotrade.models.Gender;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<GenderValidation, Gender> {

    public boolean isValid(Gender value, ConstraintValidatorContext cxt) {
        if (value == null) {
            return true;
        }

        return Arrays.asList(Gender.values()).contains(value);
    }
}
