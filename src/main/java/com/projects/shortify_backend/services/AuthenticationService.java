package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.SignInRequestDto;
import com.projects.shortify_backend.dto.SignInResponseDto;
import com.projects.shortify_backend.dto.SignupRequestDto;
import com.projects.shortify_backend.dto.SignupResponseDto;
import com.projects.shortify_backend.entities.User;
import com.projects.shortify_backend.exception.custom.EmailAlreadyExistsException;
import com.projects.shortify_backend.exception.custom.UnauthorizedAccessException;
import com.projects.shortify_backend.repository.UserRepo;
import com.projects.shortify_backend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public String getCurrentUserEmail() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null)
            throw new UnauthorizedAccessException("You're not authenticated.");

        return authentication.getName();
    }

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signup){

        if(userRepo.existsByEmail(signup.getEmail())) throw new EmailAlreadyExistsException("Email already in use.");

        userRepo.save(
                User.builder()
                        .role("USER")
                        .username(signup.getUsername())
                        .email(signup.getEmail())
                        .password(passwordEncoder.encode(signup.getPassword()))
                        .fullName(signup.getFullName())
                        .urlList(new ArrayList<>())
                        .build()
        );

        return new SignupResponseDto(true);

    }

    public SignInResponseDto signIn(SignInRequestDto request){

        var auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var userDetails = (UserDetails) auth.getPrincipal();

        if(!(userDetails instanceof User)) throw new RuntimeException("Authentication Failed");

        var token = jwtService.generateToken(auth, Instant.now().plusSeconds(1600));

        return new SignInResponseDto(token, true);

    }

}
