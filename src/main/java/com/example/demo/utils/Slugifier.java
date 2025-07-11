package com.example.demo.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Slugifier {

  private final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
  private final Pattern WHITESPACE = Pattern.compile("\\s");

  public String slugify(String input) {
    String noWhiteSpace = WHITESPACE.matcher(input).replaceAll("-");
    String normalized = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
    String slug = NON_LATIN.matcher(normalized).replaceAll("");
    return slug.toLowerCase(Locale.ENGLISH);
  }
}
