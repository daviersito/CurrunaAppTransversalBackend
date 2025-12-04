package com.example.corruna.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 🔹 Esto hace accesibles las imágenes desde http://localhost:8080/uploads/...
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}