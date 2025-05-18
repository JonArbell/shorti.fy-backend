package com.projects.shortify_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUrlRequestDto {

    @NotBlank
    private String updatedUrl;

}
