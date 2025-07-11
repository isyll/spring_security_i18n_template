package com.example.demo.validator;

import com.example.demo.repository.UsedEmailRepository;
import com.example.demo.validator.annotations.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  private final UsedEmailRepository usedEmailRepository;

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (!StringUtils.hasText(email)) {
      return true;
    }
    return !usedEmailRepository.existsById(email);
  }
}
