//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #2
//
//  File Name:    Habitat.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     3/28/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  The Habitat class that will connect to the
//  habitats table in PostgreSQL
//
//***************************************************************

package orgexample.neonarkintaketracker.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "habitats")

public class Habitat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String biome;

    @Column(nullable = false, length = 120)
    private String location;

    @Column(name = "min_temp_c", nullable = false)
    private Integer minTempC;

    @Column(name = "max_temp_c", nullable = false)
    private Integer maxTempC;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // One-To-Many habitat->many creatures
    @OneToMany(mappedBy = "habitat")
    private List<Creature> creatures;

}
