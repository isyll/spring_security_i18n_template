package com.example.demo.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.example.demo.validator.annotations.E164PhoneValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class E164PhoneValidator implements ConstraintValidator<E164PhoneValidation, String> {

  private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

  public boolean isValid(String value, ConstraintValidatorContext cxt) {
    if (!StringUtils.hasText(value)) {
      return true;
    }

    if (!value.matches("^\\+[0-9]{6,20}$")) {
      return false;
    }

    try {
      PhoneNumber number = phoneUtil.parse(value, null);
      return phoneUtil.isValidNumber(number) && phoneUtil.isPossibleNumber(number);
    } catch (NumberParseException e) {
      return false;
    }
  }
}
