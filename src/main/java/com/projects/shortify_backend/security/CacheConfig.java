package com.projects.shortify_backend.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {

        var cacheManager = new SimpleCacheManager();

        var generateCodes = caffeineCache("generatedCode", 3);

        var passageCodeForCreatingNewPassword = caffeineCache("passwordToken", 5);

        cacheManager.setCaches(
                List.of(generateCodes,passageCodeForCreatingNewPassword)
        );

        return cacheManager;

    }

    private CaffeineCache caffeineCache(String name, long duration) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(duration, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build());
    }

}
