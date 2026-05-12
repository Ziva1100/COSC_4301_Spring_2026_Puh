package org.example.neonarkintaketracker.repository;

// this repository is ment to deal with wardens'users of the system.
// it will check the credentials of the warden to see if they match
// it will return all wardens if the credentials are correct
// Capstone -- [ View All System Users ] Menu Choice

import org.example.neonarkintaketracker.dto.WardenRequest;
import org.example.neonarkintaketracker.entity.Warden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface WardenRepository extends JpaRepository<Warden, Long> {

    // basic read functionality is automatic,
    // does not need extra method declarations
    // methods:
    // save(entity)        -> insert or update a creature
    // findById(id)        -> get one creature by primary key
    // findAll()           -> get all creatures
    // deleteById(id)      -> delete by primary key
    // delete(entity)      -> delete by passing the entity itself

//    @Query(value = "SELECT w..username, w.password, r.role_name " +
//            "FROM wardens JOIN roles ON w.role_id = r.role_id " +
//            "WHERE username = :username", nativeQuery = true)
//    List<WardenRequest> getAllUsers(;

    @Query(value = "SELECT * FROM wardens WHERE username = :username", nativeQuery = true)
    Optional<Warden> findByUsername(@Param("username") String username);

    // the query kept throwing a mismatch between the query and warden request
    // had to abondon it and placed the logic of joining tables in the service controlled by entities
    @Query(value = "SELECT " +
            "    w.warden_id, w.first_name,  w.last_name, w.alternate_id, w.id_type,  w.email, r.role_name, " +
            "    c.clearance_name, w.start_date, d.dimension_name  " +
            "FROM wardens w JOIN roles r ON w.role_id = r.role_id JOIN clearances c " +
            "ON w.clearance_id = c.clearance_id JOIN dimensions d ON w.dimension_id = d.dimension_id ",
    nativeQuery = true)
    List<WardenRequest> getAllWardens();
}
