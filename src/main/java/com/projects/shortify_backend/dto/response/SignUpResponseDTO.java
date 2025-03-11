package com.projects.shortify_backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResponseDTO {

    private Long id;
    private String email;
    private String message;

}
