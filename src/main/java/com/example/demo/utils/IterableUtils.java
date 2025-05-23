package com.example.demo.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IterableUtils {
  public static <T> Set<T> arrayToSet(T[] array) {
    if (array == null) {
      throw new IllegalArgumentException("Input array cannot be null");
    }
    return Arrays.stream(array).collect(Collectors.toSet());
  }

  public static <T> List<T> iterableToList(Iterable<T> iterable) {
    List<T> list = new ArrayList<>();
    iterable.forEach(list::add);
    return list;
  }
}
