package com.example.demo.validator;

import com.example.demo.utils.IsoUtils;
import com.example.demo.validator.annotations.CountryValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class CountryValidator implements ConstraintValidator<CountryValidation, String> {

  public boolean isValid(String value, ConstraintValidatorContext cxt) {
    if (!StringUtils.hasText(value)) {
      return true;
    }

    return IsoUtils.isValidISOCountry(value);
  }
}
