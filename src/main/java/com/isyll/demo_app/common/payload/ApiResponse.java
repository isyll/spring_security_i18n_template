package com.isyll.demo_app.common.payload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.isyll.demo_app.common.utils.DateTimeUtils;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	private String message = null;
	private T data;
	private int status;
	private LocalDateTime timestamp;

	public ApiResponse(String message, T data, int status) {
		this.message = message;
		this.data = data;
		this.status = status;
		this.timestamp = DateTimeUtils.getCurrentTimestamp();
	}

	public ApiResponse(T data, int status) {
		this.data = data;
		this.status = status;
		this.timestamp = DateTimeUtils.getCurrentTimestamp();
	}

	public ApiResponse(String message, int status) {
		this.message = message;
		this.status = status;
		this.timestamp = DateTimeUtils.getCurrentTimestamp();
	}
}
