package com.isyll.demo_app.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.isyll.demo_app.common.payload.ApiResponse;

public abstract class BaseController<T> {

	public ResponseEntity<ApiResponse<T>> response(
			String message, T data, HttpStatus status) {
		return new ResponseEntity<>(
				new ApiResponse<T>(message, data, status.value()), status);
	}

	public ResponseEntity<ApiResponse<T>> response(T data, HttpStatus status) {
		return new ResponseEntity<>(
				new ApiResponse<T>(data, status.value()), status);
	}

	public ResponseEntity<ApiResponse<T>> response(T data) {
		return new ResponseEntity<>(
				new ApiResponse<T>(data, HttpStatus.OK.value()), HttpStatus.OK);
	}

}
