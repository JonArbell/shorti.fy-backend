package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.SignInRequestDto;
import com.projects.shortify_backend.dto.SignInResponseDto;
import com.projects.shortify_backend.dto.SignupRequestDto;
import com.projects.shortify_backend.dto.SignupResponseDto;
import com.projects.shortify_backend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signup){
        return new ResponseEntity<>(authenticationService.signup(signup), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto request){
        return new ResponseEntity<>(authenticationService.signIn(request),HttpStatus.OK);
    }

}
