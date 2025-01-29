package com.example.demo.core.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
    }

    public static String getCurrentPath() {
        return getRequest().getRequestURI();
    }
}
