package com.projects.shortify_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@Entity
@Table(name = "URLS")
@NoArgsConstructor
@AllArgsConstructor
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;
    private String shortUrl;
    private Instant expiryDate;
    private boolean isExpired;
    private Long numberOfClicked;
    private Long maxClicked;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
