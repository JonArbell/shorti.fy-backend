package com.projects.shortify_backend.dto.response;

import com.projects.shortify_backend.entities.URL;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class LoginResponseDTO {

    private String jwtToken;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private List<URL> urlList;

}
