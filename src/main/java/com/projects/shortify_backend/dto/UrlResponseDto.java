package com.projects.shortify_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponseDto {

    private Long id;

    private String shortUrl;

    private String originalUrl;

    private Integer numberOfClicks;

    private Integer totalClicked;

    private boolean isActive;

}
