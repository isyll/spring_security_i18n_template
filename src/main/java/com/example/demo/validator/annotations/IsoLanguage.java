package com.example.demo.validator.annotations;

import com.example.demo.validator.IsoLanguageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsoLanguageValidator.class)
@Documented
public @interface IsoLanguage {

  String message() default "{validation.language_is_invalid}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
