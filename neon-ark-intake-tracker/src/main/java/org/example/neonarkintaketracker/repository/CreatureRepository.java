//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #2
//
//  File Name:    CreatureRepository.java
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

import org.example.neonarkintaketracker.dto.CreaturesSummaryRequest;
import org.example.neonarkintaketracker.entity.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// simple CRUD with many default mathods
@Repository
public interface CreatureRepository extends JpaRepository<Creature, Long> {

    // basic read functionality is automatic,
    // does not need extra method declarations
    // methods:
    // save(entity)        -> insert or update a creature
    // findById(id)        -> get one creature by primary key
    // findAll()           -> get all creatures
    // deleteById(id)      -> delete by primary key
    // delete(entity)      -> delete by passing the entity itself

    // also paging and sorting methods

    // Capstone -- [ List All Creatures ] Menu Choice
    @Query(value = "SELECT c.id, c.name, h.biome , c.species, c.danger_level AS dangerLevel, c.condition, " +
            "c.removed FROM creatures c JOIN habitats h ON c.habitat_id = h.id", nativeQuery = true)
    List<CreaturesSummaryRequest> listAllCreatures();

    @Query(value = "SELECT c.id, c.name, h.biome , c.species, c.danger_level AS dangerLevel, c.condition, " +
            "c.removed FROM creatures c JOIN habitats h ON c.habitat_id = h.id where c.id = :id", nativeQuery = true)
    CreaturesSummaryRequest getCreatureById(@Param("id") Long id);
}
