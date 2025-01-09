package com.example.demo.validators;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateValidation, String> {

    private boolean isBeforeNow;

    @Override
    public void initialize(DateValidation constraintAnnotation) {
        this.isBeforeNow = constraintAnnotation.isBeforeNow();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            ZonedDateTime date = ZonedDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            if (isBeforeNow) {
                return date.isBefore(ZonedDateTime.now());
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}