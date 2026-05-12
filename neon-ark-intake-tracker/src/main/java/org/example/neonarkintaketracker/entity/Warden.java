package org.example.neonarkintaketracker.entity;


import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

// Capstone -- [ View All System Users ] Menu Choice
// this is an entity to cover the warden table

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wardens")
public class Warden {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warden_id")
    private Long wardenId;

    @Column(name = "alternate_id", nullable = false, unique = true)
    private int alternateId;

    @Column(name = "id_type", nullable = false, length = 10)
    private String idType;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "start_date")
    private LocalDate startDate;

    // Many-to-One relationships
    @ManyToOne(optional = false)
    @JoinColumn(name = "dimension_id", nullable = false)
    private Dimension dimension;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clearance_id", nullable = false)
    private Clearance clearance;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;
}
