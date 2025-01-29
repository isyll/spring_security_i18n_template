package com.example.demo.core.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GenderValidator.class)
public @interface GenderValidation {
    String message() default "{validation.gender_is_invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}