package com.example.demo.utils;

import com.example.demo.context.LocaleHolder;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Translator {

  private final MessageSource messageSource;

  @Resource(name = "localeHolder")
  public LocaleHolder localeHolder;

  public String t(String code, Object[] args, @Nullable Locale locale) {
    return messageSource.getMessage(
        code, args, locale != null ? locale : localeHolder.getCurrentLocale());
  }

  public String t(String code, Locale locale) {
    return t(code, null, locale);
  }

  public String t(String code, Object[] args) {
    return t(code, args, null);
  }

  public String t(String code) {
    return t(code, null, null);
  }
}
