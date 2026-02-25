package com.claudio.importcontrol.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement; // <-- Importação nova e crucial
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Define como o botão funciona
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                //Dados da API
                .info(new Info()
                        .title("Import Control API")
                        .version("1.0")
                        .description("Sistema de Controle de Importações e Rastreio de Cargas")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))

                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }
}