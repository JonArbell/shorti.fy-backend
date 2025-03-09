package com.projects.shortify_backend.entities;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    private List<URL> urlList;
}
