package com.projects.shortify_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUrlResponseDto {

    private Long id;

    private String originalUrl;

    private String password;

    private Integer maxClick;

    private LocalDate expirationDate;

}
