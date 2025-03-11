package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.LoginDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class Authentication {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO login){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword())
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
