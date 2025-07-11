package com.example.demo.validator;

import com.example.demo.repository.UsedPhoneRepository;
import com.example.demo.validator.annotations.UniquePhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class UniquePhoneValidator implements ConstraintValidator<UniquePhoneNumber, String> {

  private final UsedPhoneRepository usedPhoneRepository;

  @Override
  public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
    if (!StringUtils.hasText(phoneNumber)) {
      return true;
    }
    return !usedPhoneRepository.existsById(phoneNumber);
  }
}
