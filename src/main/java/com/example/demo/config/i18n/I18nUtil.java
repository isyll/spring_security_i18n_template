package com.example.demo.config.i18n;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class I18nUtil {

  @Resource(name = "localeHolder")
  public LocaleHolder localeHolder;

  @Autowired MessageSource messageSource;

  public String getMessage(String code, String... args) {
    return messageSource.getMessage(code, args, localeHolder.getCurrentLocale());
  }
}
