package com.example.demo.config.converter;

import com.example.demo.model.PublicId;
import jakarta.annotation.Nonnull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PublicIdConverter implements Converter<String, PublicId> {

  @Override
  public PublicId convert(@Nonnull String source) {
    return PublicId.fromBase62(source);
  }
}
