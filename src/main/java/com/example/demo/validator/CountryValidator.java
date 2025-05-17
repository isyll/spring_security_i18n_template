package com.example.demo.validator;

import com.example.demo.utils.IsoUtils;
import com.example.demo.validator.annotations.CountryValidation;
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
