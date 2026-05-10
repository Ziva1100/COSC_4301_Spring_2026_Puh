package org.example.neonarkintaketracker.repository;

// This is a feeding table repository that returns the
// feeding for a time

import org.example.neonarkintaketracker.dto.FeedingRequest;
import org.example.neonarkintaketracker.entity.Feeding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository

// Capstone -- [ Find Creatures by feeding time ] Menu Choice
// repository for feedings
public interface FeedingRepository extends JpaRepository<Feeding, Long> {
    // basic read functionality is automatic,
    // does not need extra method declarations
    // methods:
    // save(entity)        -> insert or update a creature
    // findById(id)        -> get one creature by primary key
    // findAll()           -> get all creatures
    // deleteById(id)      -> delete by primary key
    // delete(entity)      -> delete by passing the entity itself

    // join the creature and feedings table to recieve the feeding times

    @Query(value = "SELECT c.name, f.food, f.quantity, f.time " +
            "FROM creatures c JOIN feedings f ON c.id = f.creature_id " +
            "WHERE f.time = CAST(:time AS TIME)", nativeQuery = true)
    List<FeedingRequest> getFeedingTimes(@Param("time") LocalTime time);
}
