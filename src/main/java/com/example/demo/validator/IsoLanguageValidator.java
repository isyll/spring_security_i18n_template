package com.example.demo.validator;

import com.ibm.icu.util.ULocale;
import com.example.demo.validator.annotations.IsoLanguage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import org.springframework.util.StringUtils;

public class IsoLanguageValidator implements ConstraintValidator<IsoLanguage, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (!StringUtils.hasText(value)) {
      return true;
    }

    try {
      ULocale locale = new ULocale(value);
      String lang = locale.getLanguage();

      return ULocale.getISOLanguages().length > 0
          && List.of(ULocale.getISOLanguages()).contains(lang);
    } catch (Exception e) {
      return false;
    }
  }
}
