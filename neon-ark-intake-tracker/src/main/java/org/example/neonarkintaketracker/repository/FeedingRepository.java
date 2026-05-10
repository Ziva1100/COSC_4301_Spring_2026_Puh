package org.example.neonarkintaketracker.repository;

// This is a feeding table repository that returns the
// feeding for a time

import org.example.neonarkintaketracker.dto.FeedingRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository

public interface FeedingRepository {
    // basic read functionality is automatic,
    // does not need extra method declarations
    // methods:
    // save(entity)        -> insert or update a creature
    // findById(id)        -> get one creature by primary key
    // findAll()           -> get all creatures
    // deleteById(id)      -> delete by primary key
    // delete(entity)      -> delete by passing the entity itself

    @Query(value = "SELECT c.name, f.food, f.quantity, f.time " +
            "FROM creatures c JOIN feedings f ON c.id = f.creature_id " +
            "WHERE time = :time", nativeQuery = true)
    ResponseEntity<List<FeedingRequest>> getFeedingTimes(@Param("time") LocalTime time);
}
