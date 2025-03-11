package com.projects.shortify_backend.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var email = authentication.getName();
        var password = authentication.getCredentials().toString();

        log.info("Email : {}",email);
        log.info("Password : {}",password);

        var userDetails = userDetailsService.loadUserByUsername(email);

        if(!correctPassword(password,userDetails.getPassword()))
            throw new BadCredentialsException("Wrong password");

        return new UsernamePasswordAuthenticationToken(userDetails, password, authentication.getAuthorities());
    }

    private boolean correctPassword(String inputPassword, String userDetailsPassword){
        return passwordEncoder.matches(inputPassword, userDetailsPassword);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
