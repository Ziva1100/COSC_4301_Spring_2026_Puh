package org.example.neonarkintaketracker.repository;

import org.example.neonarkintaketracker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// This is a helper repository for roles so I can handle findign the right role for warden
// credentail confirmation

// Capstone -- [ View All System Users ] Menu Choice
public interface RoleRepository extends JpaRepository<Role, Long> {
    // basic read functionality is automatic,
    // does not need extra method declarations
    // methods:
    // save(entity)        -> insert or update a creature
    // findById(id)        -> get one creature by primary key
    // findAll()           -> get all creatures
    // deleteById(id)      -> delete by primary key
    // delete(entity)      -> delete by passing the entity itself
}
