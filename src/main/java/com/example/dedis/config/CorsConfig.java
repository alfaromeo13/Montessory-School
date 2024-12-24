package com.example.dedis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("*") // todo: Restrict to calls from render.com PUT FRONTEND LINK
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS") // Restrict HTTP methods
                .allowedOrigins("*")
                .exposedHeaders("*");
    }
}