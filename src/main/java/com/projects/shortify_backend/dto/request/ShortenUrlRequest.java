package com.projects.shortify_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class ShortenUrlRequest {

    @NotBlank(message = "URL cannot be blank")
    @URL(message = "Invalid URL format")
    private String url;

}
