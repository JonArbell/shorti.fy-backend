package com.projects.shortify_backend.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class URL {

    private Long id;
    private String originalUrl;
    private String shortCode;

//    @ManyToOne()
//    private User user;
}
