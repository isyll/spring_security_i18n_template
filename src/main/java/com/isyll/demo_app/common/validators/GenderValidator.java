package com.isyll.demo_app.common.validators;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<GenderValidation, String> {

	public boolean isValid(String value, ConstraintValidatorContext cxt) {
		List<String> list = Arrays.asList("male", "female", "other");
		return value != null && list.contains(value.toLowerCase());
	}
}
