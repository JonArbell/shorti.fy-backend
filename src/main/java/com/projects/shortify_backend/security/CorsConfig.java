package com.projects.shortify_backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/authentication/**")
                .allowedOrigins("http://localhost:4200","https://s-fy.netlify.app")
                .allowedMethods("POST")
                .maxAge(3600);

        registry.addMapping("/api/authenticated/**")
                .allowedOrigins("http://localhost:4200","https://s-fy.netlify.app")
                .allowedMethods("POST","GET","PUT","DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .maxAge(3600);

        registry.addMapping("/api/find-email/**")
                .allowedOrigins("http://localhost:4200","https://s-fy.netlify.app")
                .allowedMethods("GET")
                .maxAge(3600);

    }

}
