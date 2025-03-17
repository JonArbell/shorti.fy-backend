package com.projects.shortify_backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class PasswordCacheService {

    private final SecureRandom random = new SecureRandom();

    @CachePut(value = "generatedCode", key = "#email")
    public String generateCode(String email) {
        return String.format("%06d", random.nextInt(1000000));
    }

    @Cacheable(value = "generatedCode", key = "#email")
    public String getCode(String email) {
        return null;
    }

    @CachePut(value = "passwordToken", key = "#generatedCode")
    public boolean setAuthorization(String generatedCode, boolean isAuthorized){
        return isAuthorized;
    }

    @Cacheable(value = "passwordToken", key = "#generatedCode")
    public boolean isAuthorized(String generatedCode){
        return false;
    }

}
