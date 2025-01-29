package com.example.demo.core.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadRefreshTokenException extends RuntimeException {

    public BadRefreshTokenException(String message) {
        super(message);
    }
}
