package com.projects.shortify_backend.dto;

import lombok.Data;

@Data
public class SignInRequestDto {

    private String email;
    private String password;

}
