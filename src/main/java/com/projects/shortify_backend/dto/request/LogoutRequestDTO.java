package com.projects.shortify_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogoutRequestDTO {

    @NotBlank
    @NotNull
    private String token;

}
