package com.projects.shortify_backend.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecentVisitsResponseDto {

    private Long id;
    private String ipAddress;
    private String originalUrl;
    private String shortenedUrl;
    private Integer numberOfClicks;
    private boolean isActive;
}
