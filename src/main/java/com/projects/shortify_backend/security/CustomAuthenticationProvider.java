package com.projects.shortify_backend.security;

import com.projects.shortify_backend.services.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UrlService urlService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var email = authentication.getName();
        var password = authentication.getCredentials().toString();

        log.info("Email : {}",email);
        log.info("Password : {}",password);

        var userDetails = userDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Wrong password");

        var authenticatedUser = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

        log.info("Dashboard : {}", urlService.dashboard());

        log.info("Authentication : {}",authenticatedUser);

        return authenticatedUser;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
