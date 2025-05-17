package com.example.demo.validator.annotations;

import com.example.demo.validator.DateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValidation {

  String message();

  boolean isBeforeNow() default true;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
