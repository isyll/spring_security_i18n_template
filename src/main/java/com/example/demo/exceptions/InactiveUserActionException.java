package com.example.demo.exceptions;

public class InactiveUserActionException extends RuntimeException {

  public InactiveUserActionException(String message) {
    super(message);
  }
}
