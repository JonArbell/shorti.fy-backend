package com.projects.shortify_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortenUrlResponseDto {

    private boolean success;

    private String shortUrl;

    private String originalUrl;

    private LocalDate expirationDate;

    private LocalDateTime createdAt;

}
