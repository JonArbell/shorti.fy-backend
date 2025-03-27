package com.projects.shortify_backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private String jwtToken;

    private String refreshToken;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

}
