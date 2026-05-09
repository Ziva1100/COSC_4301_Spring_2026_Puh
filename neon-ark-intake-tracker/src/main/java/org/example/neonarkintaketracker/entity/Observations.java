package org.example.neonarkintaketracker.entity;

// This class is an entity for the table observations

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "observations")

// This is an entity to represent the new observations for each creature
// the columns are observation id, creature id, date recorded, category:
// [medical, bevavior, safety, accountability], and observation description

// Capstone -- [ View Observations ] Menu Choice

public class Observations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one relationship: Many observations, one creature
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name =  "creature_id", nullable = false)
    private Creature creature;

    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE NOT NULL")
    private LocalDate date;

    @Column(nullable = false)
    String category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String observation;
}
