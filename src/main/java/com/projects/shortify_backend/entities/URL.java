package com.projects.shortify_backend.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "URLS")
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;
    private String shortCode;
    private Long numOfClicked;
    private boolean isExpired;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
