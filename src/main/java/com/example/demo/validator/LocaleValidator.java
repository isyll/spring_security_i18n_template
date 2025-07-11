package com.example.demo.validator;

import com.example.demo.utils.IsoUtils;
import com.example.demo.validator.annotations.LocaleValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class LocaleValidator implements ConstraintValidator<LocaleValidation, String> {

  public boolean isValid(String value, ConstraintValidatorContext cxt) {
    if (!StringUtils.hasText(value)) {
      return true;
    }

    return IsoUtils.isValidISOLanguage(value);
  }
}
