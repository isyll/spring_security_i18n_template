package com.example.demo.utils;

import java.util.Locale;
import java.util.Set;

public class IsoUtils {
  private static final Set<String> ISO_LANGUAGES = Set.of(Locale.getISOLanguages());
  private static final Set<String> ISO_COUNTRIES = Set.of(Locale.getISOCountries());

  public static boolean isValidISOLanguage(String s) {
    return ISO_LANGUAGES.contains(s);
  }

  public static boolean isValidISOCountry(String s) {
    return ISO_COUNTRIES.contains(s);
  }
}
