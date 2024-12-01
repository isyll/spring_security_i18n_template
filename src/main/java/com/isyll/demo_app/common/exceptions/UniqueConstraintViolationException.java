package com.isyll.demo_app.common.exceptions;

import lombok.Getter;

@Getter
public class UniqueConstraintViolationException extends RuntimeException {

	private final String fieldName;

	public UniqueConstraintViolationException(String fieldName, String message) {
		super(message);
		this.fieldName = fieldName;
	}
}
