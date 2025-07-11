package com.example.demo.utils;

import jakarta.mail.internet.MimeMessage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailHelper {

  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;
  private final Translator translator;

  @Value("${MAIL_FROM_ADDRESS}")
  private String fromAddress;

  @Value("${MAIL_FROM_NAME}")
  private String fromName;

  @Value("${APP_ADMIN_EMAIL}")
  private String adminEmail;

  @Value("${NOTIFY_CRASH_EMAIL:false}")
  private Boolean notifyCrashEmail;

  public void sendEmail(String to, String subject, String body, boolean isHtml) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setFrom(fromAddress, fromName);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(body, isHtml);

      mailSender.send(message);
    } catch (Exception e) {
      throw new RuntimeException("Failed to send email", e);
    }
  }

  public void sendPasswordResetEmail(String to, String resetLink, Locale userLocale) {
    Context context = new Context(userLocale);
    context.setVariable("resetLink", resetLink);

    String htmlBody = templateEngine.process("emails/reset-password", context);
    String subject = translator.t("email.reset_password.subject", userLocale);

    sendEmail(to, subject, htmlBody, true);
  }

  public void sendUncaughtExceptionEmail(Exception exception) {
    if (!notifyCrashEmail) {
      return;
    }
    String htmlBody =
        templateEngine.process("report/uncaught-exception", buildContextFromException(exception));
    try {
      sendEmail(
          adminEmail, "Unhandled Exception Occurred : " + exception.getMessage(), htmlBody, true);
    } catch (Exception e) {
      // Avoid rethrowing: if sending this email fails, we prevent an infinite loop of exception
      // handling
      log.error("Failed to send uncaught exception email", e);
    }
  }

  private Context buildContextFromException(Exception exception) {
    Context context = new Context();

    context.setVariable("exceptionMessage", exception.getMessage());
    context.setVariable("exceptionType", exception.getClass().getName());
    context.setVariable("timestamp", LocalDateTime.now());

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    exception.printStackTrace(pw);
    context.setVariable("stackTrace", sw.toString());

    Throwable cause = exception.getCause();
    if (cause != null) {
      context.setVariable("exceptionCause", cause.toString());
    }

    return context;
  }
}
