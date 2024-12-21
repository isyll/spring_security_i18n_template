package com.isyll.demo_app.dto.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SigninRequest {

    @NotBlank(message = "{validation.username_is_mandatory}")
    private String username;

    @NotBlank(message = "{validation.password_is_mandatory}")
    private String password;
}
