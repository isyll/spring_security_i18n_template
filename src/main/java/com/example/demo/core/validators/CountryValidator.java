package com.example.demo.core.validators;

import com.example.demo.core.utils.IsoUtils;
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
