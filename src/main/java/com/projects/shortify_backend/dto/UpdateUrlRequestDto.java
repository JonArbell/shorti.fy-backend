package com.projects.shortify_backend.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUrlRequestDto {

    @NotBlank
    private String originalUrl;

    private String password;

    private Integer maxClick;

    @FutureOrPresent(message = "Expiration date must be today or in the future.")
    private LocalDate expirationDate;
}
