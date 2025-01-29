package com.example.demo.core.exceptions;

public class InactiveUserActionException extends RuntimeException {

    public InactiveUserActionException(String message) {
        super(message);
    }
}
