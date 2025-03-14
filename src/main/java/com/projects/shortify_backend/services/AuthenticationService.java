package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.request.LoginRequestDTO;
import com.projects.shortify_backend.dto.request.SignUpRequestDTO;
import com.projects.shortify_backend.dto.response.LoginResponseDTO;
import com.projects.shortify_backend.dto.response.SignUpResponseDTO;
import com.projects.shortify_backend.entities.JwtRefreshToken;
import com.projects.shortify_backend.entities.User;
import com.projects.shortify_backend.exception.custom.EmailAlreadyExistsException;
import com.projects.shortify_backend.repository.JwtRefreshTokenRepo;
import com.projects.shortify_backend.repository.UserRepo;
import com.projects.shortify_backend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final JwtRefreshTokenRepo jwtRefreshTokenRepo;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO request){

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        var jwtToken = jwtService.generateToken(auth, Instant.now().plusSeconds(1600));

        var userDetails = (UserDetails) auth.getPrincipal();

        if(!(userDetails instanceof User user)){
            throw new RuntimeException("Authentication failed");
        }

        deleteTokenByUser(user);

        var expiryDateOfRefreshToken = Instant.now().plusSeconds(3600);

        var refreshToken = jwtService.generateToken(auth, expiryDateOfRefreshToken);

        var savedRefreshToken = jwtRefreshTokenRepo.save(
                JwtRefreshToken.builder()
                        .refreshToken(refreshToken)
                        .user(user)
                        .expiryDate(expiryDateOfRefreshToken)
                        .build()
        );

        return LoginResponseDTO
                .builder()
                .refreshToken(savedRefreshToken.getRefreshToken())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .urlList(user.getUrlList())
                .jwtToken(jwtToken)
                .build();
    }

    @Transactional
    private void deleteTokenByUser(User user) {
        jwtRefreshTokenRepo.deleteByUser(user);
    }


    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO request) {

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered.");
        }

        var newUser = User.builder()
                .role("USER")
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .username(request.getUsername())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .urlList(new ArrayList<>())
                .build();

        var createdUser = userRepo.save(newUser);

        return SignUpResponseDTO.builder()
                .id(createdUser.getId())
                .email(createdUser.getEmail())
                .message("Successfully created")
                .build();
    }

}
