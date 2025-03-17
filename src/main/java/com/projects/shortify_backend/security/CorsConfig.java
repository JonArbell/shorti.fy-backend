package com.projects.shortify_backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        var allowedOrigins = new String[]{
                        "http://localhost:4200",
                        "https://s-fy.netlify.app"
                };

        registry.addMapping("/api/authentication/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("POST")
                .allowedHeaders("Content-Type")
                .maxAge(3600);

        registry.addMapping("/api/authenticated/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("POST","GET","PUT","DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .maxAge(3600);

        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET","POST")
                .allowedHeaders("Content-Type")
                .maxAge(3600);

    }

}
