package com.example.demo.constants;

import java.util.List;
import java.util.Locale;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AppMetadata {

  public final String APP_NAME = "Spring demo";
  public final String AUTHOR = "Ibrahima Sylla";
  public final String APP_VERSION = "1.0.0";
  public final Locale DEFAULT_LOCALE = Locale.FRENCH;
  public final List<Locale> SUPPORTED_LOCALES = List.of(Locale.FRENCH);
}
