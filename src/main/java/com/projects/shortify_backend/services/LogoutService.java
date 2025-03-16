package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.response.LogoutResponseDTO;
import com.projects.shortify_backend.entities.User;
import com.projects.shortify_backend.repository.JwtRefreshTokenRepo;
import com.projects.shortify_backend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final JwtRefreshTokenRepo jwtRefreshTokenRepo;

    @Transactional
    public LogoutResponseDTO logout(String token){

        var email = jwtService.extractEmail(token);

        log.info("Email : {}",email);

        var userDetails = userDetailsService.loadUserByUsername(email);

        if(userDetails instanceof User user){
            jwtRefreshTokenRepo.deleteByUser(user);
            return new LogoutResponseDTO("200","Successfully logout");
        }

        return new LogoutResponseDTO("401","Failed logout");
    }


}
