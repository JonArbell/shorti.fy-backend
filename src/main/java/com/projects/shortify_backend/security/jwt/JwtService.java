package com.projects.shortify_backend.security.jwt;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JwtService {


    public String generateToken(Authentication authentication) {
        return "";
    }

//    private final JwtEncoder jwtEncoder;
//    private final JwtEncoder jwtDecoder;

}
