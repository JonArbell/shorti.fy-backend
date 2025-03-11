package com.projects.shortify_backend.services;

import com.projects.shortify_backend.dto.request.SignUpRequestDTO;
import com.projects.shortify_backend.dto.response.SignUpResponseDTO;
import com.projects.shortify_backend.entities.User;
import com.projects.shortify_backend.exception.custom.EmailAlreadyExistsException;
import com.projects.shortify_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO request) {

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered.");
        }

        var newUser = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
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
