package com.projects.shortify_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInResponseDto {

    private String token;
    private String username;
    private boolean success;
    private String role;
}
