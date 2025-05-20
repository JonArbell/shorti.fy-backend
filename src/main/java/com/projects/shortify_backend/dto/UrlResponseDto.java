package com.projects.shortify_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlResponseDto {

    private Long id;

    private String shortUrl;

    private String password;

    private LocalDate expirationDate;

    private String originalUrl;

    private Integer maxClick;

    private Integer totalClicked;

    private boolean isActive;

    private List<VisitorResponseDto> visitorResponseDtoList;

}
