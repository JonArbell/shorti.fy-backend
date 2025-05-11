package com.projects.shortify_backend.entities;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "VISITS")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime visitedAt;

    private Integer numberOfClicks;

    @ManyToOne
    @JoinColumn(name = "url_id", nullable = false)
    private Url url;

    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;

}
