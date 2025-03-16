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

    @CachePut(value = "passwordResetCodes", key = "#email")
    public String generateCode(String email) {
        return String.format("%06d", random.nextInt(1000000));
    }

    @Cacheable(value = "passwordResetCodes", key = "#email")
    public String getCode(String email) {
        return null;
    }

}
