package com.example.demo.core.config;
import com.example.demo.core.constants.SwaggerConfig;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  private final License licence =
      new License().name(SwaggerConfig.license).url(SwaggerConfig.licenseUrl);

  private final Contact contact = new Contact().name(SwaggerConfig.contact);

  private final ExternalDocumentation docs =
      new ExternalDocumentation()
          .description("Project Documentation")
          .url(SwaggerConfig.externalDocs);

  private final Info info =
      new Info()
          .title(SwaggerConfig.title)
          .description(SwaggerConfig.description)
          .version(SwaggerConfig.SWAGGER_API_VERSION)
          .termsOfService(SwaggerConfig.termsOfServiceUrl)
          .contact(contact)
          .license(licence);

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().info(info).externalDocs(docs);
  }
}
