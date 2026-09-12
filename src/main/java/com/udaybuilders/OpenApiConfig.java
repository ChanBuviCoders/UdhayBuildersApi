package com.udaybuilders;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI udayBuildersOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Uday Builders API")
                .version("v1")
                .description("REST API for the Uday Builders contractor and property showcase platform.")
                .contact(new Contact().name("Uday Builders").email("hello@udaybuilders.in")))
            .components(new Components().addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Enter the JWT returned by POST /api/v1/auth/login.")));
    }
}
