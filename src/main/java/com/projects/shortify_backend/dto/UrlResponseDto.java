package com.projects.shortify_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlResponseDto {

    private String shortUrl;

    private String originalUrl;

}
