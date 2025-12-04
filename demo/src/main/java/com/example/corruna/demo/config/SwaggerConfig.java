package com.example.corruna.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Corruna")
                        .version("1.0")
                        .description("Documentación del backend del sistema Corruna")
                        .contact(new Contact()
                                .name("Equipo Corruna")
                                .email("soporte@corruna.com")
                                .url("https://corruna.cl")));
    }
}
