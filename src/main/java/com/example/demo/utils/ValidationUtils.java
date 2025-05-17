package com.example.demo.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

public class ValidationUtils {

  private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

  public boolean validatePhone(String phone) {
    try {
      phoneUtil.parse(phone, null);
      return phone.startsWith("+") && phoneUtil.isPossibleNumber(phone, null);
    } catch (NumberParseException e) {
      return false;
    }
  }
}
