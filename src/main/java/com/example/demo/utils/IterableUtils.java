package com.example.demo.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IterableUtils {

  public <T> Set<T> arrayToSet(T[] array) {
    if (array == null) {
      throw new IllegalArgumentException("Input array cannot be null");
    }
    return Arrays.stream(array).collect(Collectors.toSet());
  }

  public <T> List<T> iterableToList(Iterable<T> iterable) {
    List<T> list = new ArrayList<>();
    iterable.forEach(list::add);
    return list;
  }

  public <T> Set<T> toSet(Iterable<T> iterable) {
    if (iterable instanceof Collection<T> collection) {
      return new HashSet<>(collection);
    }
    Set<T> set = new HashSet<>();
    iterable.forEach(set::add);
    return set;
  }
}
