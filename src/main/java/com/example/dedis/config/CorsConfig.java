package com.example.dedis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${application.frontend.render-link}")
    private String frontendLink;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins(frontendLink)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS") // Restrict HTTP methods
                .allowedOrigins("*")
                .exposedHeaders("*");
    }
}