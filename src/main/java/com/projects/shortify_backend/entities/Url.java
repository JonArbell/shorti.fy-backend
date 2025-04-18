package com.projects.shortify_backend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "URLS")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String shortUrl;

    @Column(nullable = false)
    private String originalUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
