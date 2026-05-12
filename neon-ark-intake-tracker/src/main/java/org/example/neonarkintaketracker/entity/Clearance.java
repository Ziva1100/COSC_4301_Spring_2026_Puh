package org.example.neonarkintaketracker.entity;

import jakarta.persistence.*;
import lombok.*;

// Capstone -- [ View All System Users ] Menu Choice

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clearances")
public class Clearance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clearance_id")
    private Long clearanceId;

    @Column(name = "clearance_name", nullable = false, unique = true, length = 30)
    private String clearanceName;

    @Column(name = "clearance_desc", columnDefinition = "TEXT")
    private String clearanceDesc;
}