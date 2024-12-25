package com.example.demo.validators;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class E164PhoneValidator implements ConstraintValidator<E164PhoneValidation, String> {

    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        if (value == null) {
            return true;
        }

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            phoneUtil.parse(value, null);

            return value.startsWith("+") && phoneUtil.isPossibleNumber(value, null);
        } catch (NumberParseException e) {
            return false;
        }
    }
}
