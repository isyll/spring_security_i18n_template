package com.isyll.demo_app.common.payload.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginWithUsernameRequest {

	@NotBlank(message = "Username is mandatory")
	private String username;

	@NotBlank(message = "Password is mandatory")
	private String password;

}
