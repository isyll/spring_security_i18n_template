package com.example.demo.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  private static final String SWAGGER_API_VERSION = "1.0";
  private static final String title = "Demo application";
  private static final String description = "REST API for Demo application.";
  private static final String termsOfServiceUrl = "https://example.com/terms-of-services";
  private static final String contactEmail = "tech@example.com";
  private static final String license = "Proprietary";
  private static final String licenseUrl = "https://example.com/license";
  private static final String externalDocs = "https://example.com/docs";
  private static final License licence = new License().name(license).url(licenseUrl);
  private static final Contact contact = new Contact().name(contactEmail);
  private static final Info info =
      new Info()
          .title(title)
          .description(description)
          .version(SWAGGER_API_VERSION)
          .termsOfService(termsOfServiceUrl)
          .contact(contact)
          .license(licence);
  private static final ExternalDocumentation docs =
      new ExternalDocumentation().description("Project Documentation").url(externalDocs);

  public SwaggerConfiguration() {}

  @Bean
  public OpenAPI customOpenAPI() {
    final String securitySchemeName = "bearerAuth";

    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(
            new Components()
                .addSecuritySchemes(
                    securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
        .info(info)
        .externalDocs(docs);
  }
}
