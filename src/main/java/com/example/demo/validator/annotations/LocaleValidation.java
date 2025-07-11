package com.example.demo.validator.annotations;

import com.example.demo.validator.LocaleValidator;
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
@Constraint(validatedBy = LocaleValidator.class)
public @interface LocaleValidation {

  String message() default "{validation.locale_code_is_invalid}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
