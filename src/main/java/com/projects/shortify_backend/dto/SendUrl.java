package com.projects.shortify_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class SendUrl {

    @NotBlank(message = "URL cannot be blank")
    @URL(message = "Invalid URL format")
    private String url;

}
