//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #2
//
//  File Name:    HabitatRepository.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     3/28/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  Repository interface that will handle the
//  access to the database
//
//***************************************************************

package org.example.neonarkintaketracker.repository;

import org.example.neonarkintaketracker.entity.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// simple CRUD with many default mathods
@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Long> {

    // basic read functionality is automatic,
    // does not need extra method declarations
    // methods:
    // save(entity)        -> insert or update a Habitat
    // findById(id)        -> get one Habitat by primary key
    // findAll()           -> get all Habitat
    // deleteById(id)      -> delete by primary key
    // delete(entity)      -> delete by passing the entity itself

    // also paging and sorting methods

    // create a method to find the habitat by biome
    // Capstone -- [ Register New Creature ] Menu Choice

    Optional<Habitat> findFirstByBiome(@Param("biome") String biome);
}
