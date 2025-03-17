package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.request.PasswordResetRequest;
import com.projects.shortify_backend.dto.response.FindEmailResponse;
import com.projects.shortify_backend.exception.custom.EmailNotFoundException;
import com.projects.shortify_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class PasswordResetService {

    private final UserRepo userRepo;
    private final PasswordCacheService passwordCacheService;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    public FindEmailResponse findEmail(String email){

        var findUser = userRepo.findByEmail(email);

        if(findUser.isEmpty())
            throw new EmailNotFoundException("Can't find this email.");

        return new FindEmailResponse(findUser.get().getEmail());
    }

    public void sendCodeToEmail(String email){

        var findUser = userRepo.findByEmail(email);

        if(findUser.isEmpty())
            throw new EmailNotFoundException("Can't find this email.");

        var generatedCode = passwordCacheService.generateCode(email);

        var message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Shorti.fy Forgot Password Code");
        message.setText("Your password reset code: " + generatedCode + "\n\nThis code will expire in 2 minutes.");
        javaMailSender.send(message);
    }

    public boolean changePassword(PasswordResetRequest passwordResetRequest, String code){

        var findUserByEmail = userRepo.findByEmail(passwordResetRequest.getEmail());

        if(findUserByEmail.isEmpty())
            throw new EmailNotFoundException("Email not found.");

        if(passwordCacheService.isAuthorized(code+passwordResetRequest.getEmail())){

            var updatedPassword = findUserByEmail.get();

            updatedPassword.setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));

            userRepo.save(updatedPassword);

            return true;
        }
        return false;
    }

    public boolean areCodesEqual(String email, String code){

        var storedCode = passwordCacheService.getCode(email);

        log.info("Input code : {}",code);
        log.info("Get code : {}", storedCode);

        return storedCode != null && storedCode.equals(code);
    }


}
