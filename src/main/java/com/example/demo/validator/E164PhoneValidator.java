package com.example.demo.validator;

import com.example.demo.validator.annotations.E164PhoneValidation;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class E164PhoneValidator implements ConstraintValidator<E164PhoneValidation, String> {
  private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

  public boolean isValid(String value, ConstraintValidatorContext cxt) {
    if (value == null) {
      return true;
    }

    try {
      phoneUtil.parse(value, null);
      return value.startsWith("+") && phoneUtil.isPossibleNumber(value, null);
    } catch (NumberParseException e) {
      return false;
    }
  }
}
