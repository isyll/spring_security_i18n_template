package com.example.demo.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = E164PhoneValidator.class)
public @interface E164PhoneValidation {
  String message() default "{validation.phone_is_invalid}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
