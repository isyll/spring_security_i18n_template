package com.example.demo.utils;

import java.util.Locale;
import java.util.Set;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IsoUtils {

  private final Set<String> ISO_LANGUAGES = Set.of(Locale.getISOLanguages());
  private final Set<String> ISO_COUNTRIES = Set.of(Locale.getISOCountries());

  public boolean isValidISOLanguage(String s) {
    return ISO_LANGUAGES.contains(s);
  }

  public boolean isValidISOCountry(String s) {
    return ISO_COUNTRIES.contains(s);
  }
}
