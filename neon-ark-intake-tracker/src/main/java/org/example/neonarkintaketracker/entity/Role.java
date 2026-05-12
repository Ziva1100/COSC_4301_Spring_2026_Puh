package org.example.neonarkintaketracker.entity;

import jakarta.persistence.*;
import lombok.*;

// Capstone -- [ View All System Users ] Menu Choice
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name", nullable = false, unique = true, length = 30)
    private String roleName;

    @Column(name = "role_desc", columnDefinition = "TEXT")
    private String roleDesc;
}