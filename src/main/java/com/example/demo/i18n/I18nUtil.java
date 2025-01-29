package com.example.demo.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
public class I18nUtil {

	@Autowired
	MessageSource messageSource;

	@Resource(name = "localeHolder")
	public LocaleHolder localeHolder;

	public String getMessage(String code, String... args) {
		return messageSource.getMessage(code, (Object[]) args, localeHolder.getCurrentLocale());
	}
}
