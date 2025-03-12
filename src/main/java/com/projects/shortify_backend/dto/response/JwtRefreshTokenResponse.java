package com.projects.shortify_backend.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class JwtRefreshTokenResponse {

    private Long id;

    private String jwtToken;

    private String refreshToken;

    private Instant expiryDate;

    private Long userId;

}
