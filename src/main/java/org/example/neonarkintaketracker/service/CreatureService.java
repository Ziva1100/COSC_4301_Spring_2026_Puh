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
    //  Method:       getAllCreatures
    //
    //  Description:  returns all creatures in the database
    //
    //  Parameters:   N/A
    //
    //  Returns:      List<Creature>
    //
    //**************************************************************
    public Optional<Creature> getCreatureById(Long id){
        return repository.findById(id);
    }

}
