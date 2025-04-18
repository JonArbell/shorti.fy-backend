package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.SignupRequestDto;
import com.projects.shortify_backend.dto.SignupResponseDto;
import com.projects.shortify_backend.entities.User;
import com.projects.shortify_backend.exception.custom.EmailAlreadyExistsException;
import com.projects.shortify_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
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

}
