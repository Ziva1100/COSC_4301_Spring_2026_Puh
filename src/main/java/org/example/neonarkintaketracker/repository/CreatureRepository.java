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

import org.example.neonarkintaketracker.entity.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// simple CRUD with many default mathods
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
}
