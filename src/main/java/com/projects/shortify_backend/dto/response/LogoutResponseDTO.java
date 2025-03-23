package com.projects.shortify_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogoutResponseDTO {

    private String status;

    private String message;

}
