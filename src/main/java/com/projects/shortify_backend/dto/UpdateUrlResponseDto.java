package com.projects.shortify_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUrlResponseDto {

    private String oldLongUrl;
    private String updatedLongUrl;
    private String message;

}
