package com.projects.shortify_backend.dto.response.base;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitorBaseResponseDTO {

    private Long id;

    private String ipAddress;

    private String location;

    private String device;

    private Long numberOfClicked;
}
