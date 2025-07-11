package com.example.demo.validator;

import com.example.demo.dto.school.AcademicYearDto;
import com.example.demo.validator.annotations.ValidDateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, AcademicYearDto> {

  @Override
  public boolean isValid(AcademicYearDto dto, ConstraintValidatorContext context) {
    if (dto.startDate() == null || dto.endDate() == null) {
      return true;
    }
    return dto.endDate().isAfter(dto.startDate());
  }
}
