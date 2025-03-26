package com.projects.shortify_backend.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long numberOfVisit;

    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @JoinColumn(name = "url_id")
    private URL url;

}
