package com.isyll.demo_app.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.isyll.demo_app.common.exceptions.ResourceNotFoundException;
import com.isyll.demo_app.common.payload.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Invalid UUID Base62 format. Must be 22 alphanumeric characters.");
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(
				"An error occurred while processing the request. Please try again later.",
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				System.currentTimeMillis());

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
