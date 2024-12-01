package com.isyll.demo_app.config.constants;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AppConfig {

	public static final String APP_NAME = "Demo App";
	public static final String APP_VERSION = "1.0.0";
	public static final String APP_DESCRIPTION = "This is a demo Spring boot app.";
	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
	public static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(Locale.ENGLISH, Locale.FRENCH);

}
