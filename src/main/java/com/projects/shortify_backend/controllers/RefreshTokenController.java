package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.response.JwtRefreshTokenResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.util.Map;

@Slf4j
@RequestMapping("/api/authenticated")
@RestController
public class RefreshTokenController {

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtRefreshTokenResponseDTO> refreshToken(@RequestBody Map<String, String> map){

        log.info("Test : {}",map);

        var response = JwtRefreshTokenResponseDTO.builder()
                .id(1L)
                .jwtToken("")
                .userId(2L)
                .expiryDate(Instant.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
