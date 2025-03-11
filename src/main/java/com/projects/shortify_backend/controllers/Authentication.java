package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.request.LoginRequestDTO;
import com.projects.shortify_backend.dto.request.SignUpRequestDTO;
import com.projects.shortify_backend.dto.response.LoginResponseDTO;
import com.projects.shortify_backend.entities.User;
import com.projects.shortify_backend.security.jwt.JwtService;
import com.projects.shortify_backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class Authentication {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO login){

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword())
        );

        var jwtToken = jwtService.generateToken(auth);

        var userDetails = (UserDetails) auth.getPrincipal();

        if(!(userDetails instanceof User user)){
            throw new RuntimeException("Authentication failed");
        }

        var response = LoginResponseDTO
                .builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .urlList(new ArrayList<>())
                .jwtToken(jwtToken)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDTO signUp){

        var response = userService.signUp(signUp);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}
