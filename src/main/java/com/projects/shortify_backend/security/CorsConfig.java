package com.projects.shortify_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();

        var domains = List.of(
                "http://localhost:4200",
                "http://192.168.0.103:4200",
                "https://s-fy.netlify.app"
        );

        // 🔓 Public CORS config (no auth header required)
        var publicConfig = new CorsConfiguration();
        publicConfig.setAllowedOrigins(domains);
        publicConfig.setAllowedMethods(List.of("POST"));
        publicConfig.setAllowedHeaders(List.of("Content-Type"));


        // 🔐 Authenticated CORS config (needs auth header)
        var authenticatedConfig = new CorsConfiguration();
        authenticatedConfig.setAllowedOrigins(domains);

        authenticatedConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        authenticatedConfig.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // 🌐 Fallback for other /api endpoints
        var generalConfig = new CorsConfiguration();
        generalConfig.setAllowedOrigins(domains);
        generalConfig.setAllowedMethods(List.of("GET", "POST", "PUT"));
        generalConfig.setAllowedHeaders(List.of("Content-Type"));

        var verify = new CorsConfiguration();
        verify.setAllowedOrigins(domains);
        verify.setAllowedMethods(List.of("GET", "POST", "PUT"));

        // 🔗 Register each config per route pattern
        source.registerCorsConfiguration("/api/authentication/**", publicConfig);
        source.registerCorsConfiguration("/api/authenticated/**", authenticatedConfig);
        source.registerCorsConfiguration("/api/**", generalConfig); // fallback for others
        source.registerCorsConfiguration("/api/validate-url/**", verify);

        return source;
    }


}
