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
public class UpdateUrlResponseDto {

    private String oldLongUrl;
    private String updatedLongUrl;
    private String message;

    private Integer maxClick;

    private LocalDate expirationDate;

}
