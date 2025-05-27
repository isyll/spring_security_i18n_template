package com.example.demo.config.i18n;

import com.example.demo.config.constants.AppConstants;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class I18nConfig implements WebMvcConfigurer {
  private final LocaleInterceptor localeInterceptor;

  public I18nConfig(LocaleInterceptor localeInterceptor) {
    this.localeInterceptor = localeInterceptor;
  }

  @Bean
  public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
    final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
    localValidatorFactoryBean.setValidationMessageSource(messageSource);
    return localValidatorFactoryBean;
  }

  @Override
  public void addInterceptors(InterceptorRegistry interceptorRegistry) {
    interceptorRegistry.addInterceptor(localeInterceptor);
  }

  @Bean
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  LocaleHolder localeHolder() {
    return new LocaleHolder();
  }

  @Bean
  LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName("lang");
    return interceptor;
  }

  @Bean
  AcceptHeaderLocaleResolver acceptHeaderLocaleResolver() {
    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    localeResolver.setSupportedLocales(AppConstants.SUPPORTED_LOCALES);
    localeResolver.setDefaultLocale(AppConstants.DEFAULT_LOCALE);
    return localeResolver;
  }

  @Bean
  MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();

    messageSource.setBasename("classpath:/messages");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setUseCodeAsDefaultMessage(true);
    return messageSource;
  }
}
