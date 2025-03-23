package com.projects.shortify_backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponseDTO {

    private Long overallTotalUrlLinks;

    private Long totalActiveUrlLinks;

    private Long totalExpiredUrlLinks;

    private String mostClickedUrl;

}
