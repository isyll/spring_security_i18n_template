package com.example.demo.core.exceptions;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UniqueConstraintViolationException extends RuntimeException {

  private final Map<String, String> errors;
}
