package com.example.demo.utils;

import java.security.SecureRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PasswordGenerator {

  private final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
  private final String DIGITS = "0123456789";
  private final String SYMBOLS = "!@#$%&*-_+=?/";

  private final String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS + SYMBOLS;
  private final SecureRandom RANDOM = new SecureRandom();

  public String generatePassword(int length) {
    StringBuilder password = new StringBuilder();
    if (length < 4) {
      throw new IllegalArgumentException("Cannot generate password less than 4 characters.");
    }

    password.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));
    password.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));
    password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
    password.append(SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length())));

    for (int i = 4; i < length; i++) {
      password.append(ALL_CHARS.charAt(RANDOM.nextInt(ALL_CHARS.length())));
    }

    return shuffleString(password.toString());
  }

  private String shuffleString(String input) {
    char[] array = input.toCharArray();
    for (int i = array.length - 1; i > 0; i--) {
      int index = RANDOM.nextInt(i + 1);
      char temp = array[i];
      array[i] = array[index];
      array[index] = temp;
    }
    return new String(array);
  }
}
