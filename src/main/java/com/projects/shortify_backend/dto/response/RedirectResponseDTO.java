package com.projects.shortify_backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RedirectResponseDTO {

    private String maskedIpAddress;

    private String location;

    private String device;

    private Integer numberOfVisit;

    private String originalUrl;

}
