package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.request.LoginRequestDTO;
import com.projects.shortify_backend.dto.request.SignUpRequestDTO;
import com.projects.shortify_backend.dto.response.LoginResponseDTO;
import com.projects.shortify_backend.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO login){

        var response = authenticationService.login(login);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDTO signUp){

        var response = authenticationService.signUp(signUp);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}
