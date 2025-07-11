package com.example.demo.config;

import com.example.demo.interceptor.LocaleInterceptor;
import com.example.demo.interceptor.MustChangePasswordInterceptor;
import com.example.demo.interceptor.SchoolInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final SchoolInterceptor schoolInterceptor;
  private final MustChangePasswordInterceptor mustChangePasswordInterceptor;
  private final LocaleInterceptor localeInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(schoolInterceptor);
    registry.addInterceptor(mustChangePasswordInterceptor);
    registry.addInterceptor(localeInterceptor);
  }
}
