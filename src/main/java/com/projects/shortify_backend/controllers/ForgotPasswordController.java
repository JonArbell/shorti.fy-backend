package com.projects.shortify_backend.controllers;

import com.projects.shortify_backend.dto.request.PasswordResetRequest;
import com.projects.shortify_backend.dto.response.FindEmailResponse;
import com.projects.shortify_backend.services.PasswordCacheService;
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
    private final PasswordCacheService passwordCacheService;

    @PostMapping("/generate-code")
    public ResponseEntity<Map<String, String>> generateCode(@RequestBody Map<String, String> email){

        passwordResetService.sendCodeToEmail(email.get("email"));

        return new ResponseEntity<>(Map.of("message","success"), HttpStatus.OK);
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<Map<String, Boolean>> compareCode(@RequestBody Map<String, String> inputCode,
                                                           @PathVariable String email){

        var code = inputCode.get("code");

        var isEqual = passwordResetService.areCodesEqual(email, code);

        var isAuthorized = passwordCacheService.setAuthorization(code+email, isEqual);

        return new ResponseEntity<>(Map.of("isAuthorized", isAuthorized), HttpStatus.OK);
    }

    @GetMapping("/find-email/{email}")
    public ResponseEntity<FindEmailResponse> findEmail(@PathVariable String email){

        var findEmail = passwordResetService.findEmail(email);

        return new ResponseEntity<>(findEmail, HttpStatus.OK);
    }


    @PutMapping("/forgot-password")
    public ResponseEntity<Map<String, Boolean>> changePassword(@RequestBody PasswordResetRequest passwordResetRequest,
                                                 @RequestParam String code){

        var passwordChanged = passwordResetService.changePassword(passwordResetRequest, code);

        return new ResponseEntity<>(Map.of("isPasswordChanged",passwordChanged),HttpStatus.OK);
    }

}
