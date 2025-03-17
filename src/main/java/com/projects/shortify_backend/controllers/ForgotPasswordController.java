package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.response.FindEmailResponse;
import com.projects.shortify_backend.services.PasswordResetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ForgotPasswordController {

    private final PasswordResetService passwordResetService;


    @PostMapping("/generate-code")
    public ResponseEntity<Map<String, String>> generateCode(@RequestBody Map<String, String> email){

        passwordResetService.sendCodeToEmail(email.get("email"));

        return new ResponseEntity<>(Map.of("message","success"), HttpStatus.OK);
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<Map<String, String>> compareCode(@RequestBody Map<String, String> code,
                                                           @PathVariable String email){

        var isEqual = passwordResetService.areCodesEqual(email, code.get("code"));

        return new ResponseEntity<>(Map.of("message",isEqual ? "Yes equal!" : "No botoy"), HttpStatus.OK);
    }


    @GetMapping("/find-email/{email}")
    public ResponseEntity<FindEmailResponse> findEmail(@PathVariable String email){

        var findEmail = passwordResetService.findEmail(email);

        return new ResponseEntity<>(findEmail, HttpStatus.OK);
    }


}
