package com.projects.shortify_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddUrlRequestDto {

    @NotBlank(message = "Original URL must not be empty")   // Ensures URL is not null or empty
    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*$", message = "Invalid URL format") // Checks URL format
    private String originalUrl;

}
