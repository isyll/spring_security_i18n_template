package com.example.demo.config.i18n;

import java.util.Locale;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.example.demo.config.constants.AppConfig;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LocaleInterceptor implements HandlerInterceptor {

	@Resource(name = "localeHolder")
	LocaleHolder localeHolder;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);

		if (localeResolver == null) {
			throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
		}

		if (localeResolver instanceof AcceptHeaderLocaleResolver headerLocaleResolver) {
			localeHolder.setCurrentLocale(determineLocale(headerLocaleResolver, request));
		} else {
			throw new IllegalStateException("Resolver should be of AcceptHeaderLocaleResolver type");
		}

		return true;
	}

	Locale determineLocale(AcceptHeaderLocaleResolver headerLocaleResolver, HttpServletRequest request) {
		Locale locale = headerLocaleResolver.resolveLocale(request);

		if (AppConfig.SUPPORTED_LOCALES.contains(locale)) {
			return locale;
		}

		return AppConfig.DEFAULT_LOCALE;
	}
}
