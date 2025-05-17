package com.example.demo.validator;

import com.example.demo.model.Gender;
import com.example.demo.validator.annotations.GenderValidation;
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
