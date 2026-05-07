//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #2
//
//  File Name:    Creature.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     3/28/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  The Creature class that will connect to the
//  creatures table in PostgreSQL
//
//***************************************************************

package org.example.neonarkintaketracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "creatures")
public class Creature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 120)
    private String species;

    @Column(name = "danger_level", nullable = false, length = 30)
    private String dangerLevel;

    @Column(nullable = false, length = 30)
    private String condition;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Many-to-One Many Creatures -> One Habitat
    @ManyToOne(optional = false)
    @JsonBackReference
    @JoinColumn(name = "habitat_id", nullable = false)
    private Habitat habitat;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private int removed;

}
