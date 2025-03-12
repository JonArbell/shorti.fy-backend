package com.projects.shortify_backend.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.time.Instant;

@Data
@Builder
@Entity
@Table(name = "Jwt_Refresh_Tokens")
public class JwtRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String refreshToken;

    private Instant expiryDate;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

}
