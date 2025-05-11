package com.projects.shortify_backend.dto;

import lombok.Data;

@Data
public class RecentVisitsRequestDto {

    private String ipAddress;
    private String originalUrl;
    private String shortenedUrl;
    private Long numberOfClicks;
    private boolean isActive;
}
