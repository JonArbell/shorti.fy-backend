package com.projects.shortify_backend.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import java.time.Instant;

@Data
@SuperBuilder
public class UrlsResponseDTO {

    private Long id;

    private String originalUrl;

    private String shortUrl;

    private Long numberOfClicked;

    private Long maxClicked;

    private Instant expiryDate;

    private boolean isExpired;
    
}
