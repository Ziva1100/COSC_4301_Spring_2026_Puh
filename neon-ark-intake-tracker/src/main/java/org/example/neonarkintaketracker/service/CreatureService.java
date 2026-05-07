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
import org.example.neonarkintaketracker.dto.CreaturesSummaryRequest;
import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.entity.Habitat;
import org.example.neonarkintaketracker.repository.CreatureRepository;
import org.example.neonarkintaketracker.repository.HabitatRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class CreatureService {
    private final CreatureRepository repository;
    private final HabitatRepository habitatRepository;

    public CreatureService(CreatureRepository repository, HabitatRepository habitatRepository) {

        this.repository = repository;
        this.habitatRepository = habitatRepository;
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

        // what client submits as a new creauture. other fieds
        // are field automatically
        Creature creature = new Creature();

        // CreatureRequest fields are name, species, danger level, condition
        creature.setName(req.name());
        creature.setSpecies(req.species());
        creature.setDangerLevel(req.dangerLevel());
        creature.setCondition(req.condition());

        // find the correct habitatId and map it to the creauture creation
        Habitat habitat = habitatRepository.findById(req.habitatId())
                .orElseThrow(() -> new RuntimeException("Habitat ID not found: "
                + req.habitatId()));

        creature.setHabitat(habitat);

        // save the CreatedAt
        creature.setCreatedAt(LocalDateTime.now());

        // save the creature to the database
        Creature saved = repository.save(creature);

        // CreatureResponse fields: id, name, spieces, dangerLevel,
        // condition, createdAt
        CreatureResponse res = new CreatureResponse(
                saved.getId(),
                saved.getName(),
                saved.getSpecies(),
                saved.getDangerLevel(),
                saved.getCondition(),
                saved.getHabitat().getId(),
                // ensure the type missmatch between LocalDateTime and Instant
                saved.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()
        );

        return res;
    }

    // Capstone -- [ List All Creatures ] Menu Choice
    public List<CreaturesSummaryRequest> getAllCreatureSummary(){
        return repository.listAllCreatures();
    }

}
