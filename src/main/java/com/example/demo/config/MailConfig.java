package com.example.demo.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

  @Value("${MAIL_HOST:smtp.gmail.com}")
  private String host;

  @Value("${MAIL_PORT:587}")
  private int port;

  @Value("${MAIL_USERNAME}")
  private String username;

  @Value("${MAIL_PASSWORD}")
  private String password;

  @Value("${MAIL_PROTOCOL:smtp}")
  private String protocol;

  @Value("${MAIL_SMTP_AUTH:true}")
  private String smtpAuth;

  @Value("${MAIL_STARTTLS:true}")
  private String startTls;

  @Value("${MAIL_DEBUG:false}")
  private String debug;

  @Bean
  JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", protocol);
    props.put("mail.smtp.auth", smtpAuth);
    props.put("mail.smtp.starttls.enable", startTls);
    props.put("mail.debug", debug);

    return mailSender;
  }
}
