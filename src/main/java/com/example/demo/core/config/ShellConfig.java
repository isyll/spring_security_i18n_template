package com.example.demo.core.config;

import org.jline.utils.AttributedString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

import com.example.demo.core.constants.AppConfig;

@Configuration
public class ShellConfig {

    @Bean
    PromptProvider promptProvider() {
        return () -> new AttributedString(AppConfig.APP_NAME + "> ");
    }
}
