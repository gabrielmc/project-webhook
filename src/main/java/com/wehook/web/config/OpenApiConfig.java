package com.wehook.web.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Products API")
                .version("1.0.0")
                .description("API de gerenciamento de produtos com suporte a Webhooks")
                .contact(new Contact()
                    .name("Gabriel Muniz")
                    .email("munhosga@email.com")
                    .url("github.com/gabrielmc")))
            /*.externalDocs(new ExternalDocumentation()
                .description("Repositório")
                .url("https://"))*/
            // configuração JWT para quando adicionar segurança
            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
            .components(new Components()
                .addSecuritySchemes("BearerAuth", new SecurityScheme()
                    .name("BearerAuth")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }
}