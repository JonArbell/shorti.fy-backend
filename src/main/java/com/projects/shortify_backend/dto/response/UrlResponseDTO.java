package com.projects.shortify_backend.dto.response;

import com.projects.shortify_backend.dto.response.base.VisitorBaseResponseDTO;
import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class UrlResponseDTO {

    private Long id;

    private String originalUrl;

    private String shortUrl;

    private Long numberOfClicked;

    private Long maxClicked;

    private Instant expiryDate;

    private boolean isExpired;

    private List<VisitorBaseResponseDTO> visitors;

}
