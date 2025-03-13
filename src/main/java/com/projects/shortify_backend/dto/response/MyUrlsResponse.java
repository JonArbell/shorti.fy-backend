package com.projects.shortify_backend.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class MyUrlsResponse {

    private Long id;
    private String originalUrl;
    private String shortUrl;
    private Long currentClicked;
    private Long maxClicked;
    private Instant expiryDate;
    private boolean isExpired;

}
