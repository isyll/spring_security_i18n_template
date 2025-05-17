package com.example.demo.config.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
  private static final String SWAGGER_API_VERSION = "1.0.0";
  private static final String title = "Demo application";
  private static final String description = "REST API for Demo application.";
  private static final String termsOfServiceUrl = "https://example.com/terms-of-services";
  private static final String contactEmail = "tech@example.com";
  private static final String license = "Proprietary";
  private static final String licenseUrl = "https://example.com/license";
  private static final String externalDocs = "https://example.com/docs";

  private final License licence = new License().name(license).url(licenseUrl);

  private final Contact contact = new Contact().name(contactEmail);

  private final ExternalDocumentation docs =
      new ExternalDocumentation().description("Project Documentation").url(externalDocs);

  private final Info info =
      new Info()
          .title(title)
          .description(description)
          .version(SWAGGER_API_VERSION)
          .termsOfService(termsOfServiceUrl)
          .contact(contact)
          .license(licence);

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().info(info).externalDocs(docs);
  }
}
