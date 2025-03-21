package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.request.LogoutRequestDTO;
import com.projects.shortify_backend.dto.response.LogoutResponseDTO;
import com.projects.shortify_backend.services.LogoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/authenticated")
@RequiredArgsConstructor
@RestController
public class LogoutController {

    private final LogoutService logoutService;

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDTO> logout(@RequestBody @Valid LogoutRequestDTO logout){

        log.info("Logout object : {}",logout);

        var response = logoutService.logout(logout.getToken());

        if(response.getStatus().equals("401"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
