package org.example.neonarkintaketracker.entity;

// this class will create an entity associated with the feeding table
// Capstone -- [ Find Creatures by feeding time ] Menu Choice

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "feedings")
public class Feeding
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many - to - one relationship
    // add creature's foreign key
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "creature_id", nullable = false)
    private Creature creature;

    @Column(nullable = false)
    private String food;

    @Column(nullable = false)
    private String quantity;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private int active;

}
