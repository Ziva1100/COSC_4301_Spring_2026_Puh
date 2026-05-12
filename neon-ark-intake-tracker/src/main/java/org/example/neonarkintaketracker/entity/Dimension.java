package org.example.neonarkintaketracker.entity;

import jakarta.persistence.*;
import lombok.*;

// Capstone -- [ View All System Users ] Menu Choice

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dimensions")
public class Dimension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dimension_id")
    private Long dimensionId;

    @Column(name = "dimension_name", nullable = false, unique = true, length = 30)
    private String dimensionName;

    @Column(name = "dimension_desc", columnDefinition = "TEXT")
    private String dimensionDesc;
}