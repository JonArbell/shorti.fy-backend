package com.projects.shortify_backend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ShortenUrlResponseDTO {

    private Long id;
    private String shortUrl;
    private String originalUrl;
    private Integer maxClicked;
    private Instant expiryDate;
    private boolean isExpired;
    private Integer numberOfClicked;

}
