package com.example.demo.utils;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CodeGenerator {

  public String generateSchoolCode(String name) {
    String normalized =
        Normalizer.normalize(name, Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}", "");

    String[] words = normalized.toLowerCase().replaceAll("[^a-z0-9 ]+", "").split("\\s+");

    int maxWords = 4;
    return Arrays.stream(words).limit(maxWords).collect(Collectors.joining("-"));
  }
}
