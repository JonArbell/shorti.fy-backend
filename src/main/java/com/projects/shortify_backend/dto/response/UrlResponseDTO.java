package com.projects.shortify_backend.dto.response;

import com.projects.shortify_backend.dto.response.base.VisitorBaseResponseDTO;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import java.util.List;


@Data
@SuperBuilder
public class UrlResponseDTO extends UrlsResponseDTO {
    private List<VisitorBaseResponseDTO> visitors;
}
