package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.config.constants.SwaggerConfig;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguration {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title(SwaggerConfig.title)
                                                .description(SwaggerConfig.description)
                                                .version(SwaggerConfig.SWAGGER_API_VERSION)
                                                .termsOfService(SwaggerConfig.termsOfServiceUrl)
                                                .contact(new Contact().name(SwaggerConfig.contact))
                                                .license(new License()
                                                                .name(SwaggerConfig.license)
                                                                .url(SwaggerConfig.licenseUrl)))
                                .externalDocs(new ExternalDocumentation()
                                                .description("Project Documentation")
                                                .url("https://www.demo-app.com/docs"));
        }
}
