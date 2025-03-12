package com.projects.shortify_backend.services;

import com.projects.shortify_backend.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDetailsService userDetailsService;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Authentication from User Service : {}",authentication);

        if (authentication != null && authentication.isAuthenticated()) {

            var principal = authentication.getPrincipal();

            log.info("Principal : {}", principal);

            if (authentication.getPrincipal() instanceof Jwt jwt) {

                var email = jwt.getSubject();

                log.info("Email : {}", email);

                return (User) userDetailsService.loadUserByUsername(email);
            }
        }

        return null;
    }

}
