package com.projects.shortify_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class ShortenUrlRequestDto {

    @NotBlank(message = "Original URL must not be empty")   // Ensures URL is not null or empty
    @URL(message = "Invalid URL format")
    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*$", message = "Invalid URL format") // Checks URL format
    private String originalUrl;

}
