package com.projects.shortify_backend.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDto {
    private Long totalUrlLinks;
    private Long activeUrls;
    private String mostClickedUrl;
    private Long expiredUrls;
}
