package com.example.demo.core.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class IterableUtils {

    public static <T> Set<T> arrayToSet(T[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        return Arrays.stream(array).collect(Collectors.toSet());
    }
}
