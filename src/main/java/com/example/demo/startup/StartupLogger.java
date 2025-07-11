package com.example.demo.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
public class StartupLogger {

  @Bean
  @Profile("dev")
  public CommandLineRunner logStartupInfo(Environment env) {
    return args -> {
      System.out.println("🧠 Demo app - Dev Mode ON");
      System.out.println("🔧 Active profile : " + String.join(", ", env.getActiveProfiles()));
      System.out.println("🌐 Server port    : " + env.getProperty("server.port"));
      System.out.println("🗃️  DB URL        : " + env.getProperty("spring.datasource.url"));
      System.out.println("📬 Mail host      : " + env.getProperty("spring.mail.host"));
    };
  }
}
