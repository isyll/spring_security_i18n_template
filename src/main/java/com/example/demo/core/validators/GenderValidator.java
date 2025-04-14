package com.example.demo.core.validators;

import com.example.demo.models.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class GenderValidator implements ConstraintValidator<GenderValidation, Gender> {

  public boolean isValid(Gender value, ConstraintValidatorContext cxt) {
    if (value == null) {
      return true;
    }

    return Arrays.asList(Gender.values()).contains(value);
  }
}
