package ru.solovyev.counterpartyDirectory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Конфигурация сваггера.
 */
@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket produceApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("ru.solovyev.counterpartyDirectory.controller"))
                .build();
    }
}