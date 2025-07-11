package com.example.demo.utils;

import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Lazy(false)
@Component
public class BeanContextUtils implements ApplicationContextAware {

  private static ApplicationContext context;

  public static <T> T getBean(Class<T> clazz) throws BeansException {
    Assert.state(
        context != null, "Spring context in the BeanContextFactory is not been initialized yet!");
    return context.getBean(clazz);
  }

  @Override
  public void setApplicationContext(@Nonnull ApplicationContext ctx) {
    BeanContextUtils.context = ctx;
  }
}
