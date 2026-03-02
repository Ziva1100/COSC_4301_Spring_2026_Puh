//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #2
//
//  File Name:    CreatureService.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     3/28/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  The class that handles business logic
//  for the Creature Class
//
//***************************************************************

package org.example.neonarkintaketracker.service;

import org.example.neonarkintaketracker.dto.CreatureRequest;
import org.example.neonarkintaketracker.dto.CreatureResponse;
import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.repository.CreatureRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CreatureService {
    private final CreatureRepository repository;

    public CreatureService(CreatureRepository repository) {
        this.repository = repository;
    }

    //***************************************************************
    //
    //  Method:       getAllCreatures
    //
    //  Description:  returns all creatures in the database
    //
    //  Parameters:   N/A
    //
    //  Returns:      List<Creature>
    //
    //**************************************************************
    public List<Creature> getAllCreatures(){
        return repository.findAll();
    }

    //***************************************************************
    //
    //  Method:       getCreatureById
    //
    //  Description:  returns creature based on ID, uses Optional
    //
    //  Parameters:   Long id
    //
    //  Returns:      Creature or Null
    //
    //**************************************************************
    public Optional<Creature> getCreatureById(Long id){
        return repository.findById(id);
    }

    //***************************************************************
    //
    //  Method:       createCreature
    //
    //  Description:  handles DTOs for the creatures creation
    //
    //  Parameters:   DTO CreatureRequest
    //
    //  Returns:      CreatureResponse DTO
    //
    //**************************************************************
    public CreatureResponse createCreature(CreatureRequest req){
        Creature creature = new Creature();

        Creature saved = repository.save(creature);

        CreatureResponse res = new CreatureResponse();

        return res;
    }

}
