package com.spsolutions.newsweb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger / OpenAPI metadata. Swagger UI is served at {@code /swagger-ui.html}
 * and the raw spec at {@code /v3/api-docs}.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI newsWebOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("News Web API")
                .description("REST API for browsing news categories, reading news items, and posting comments.")
                .version("1.0.0")
                .contact(new Contact().name("SP Solutions")));
    }
}
