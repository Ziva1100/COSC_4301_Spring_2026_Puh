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
//  Description:  the class that handles http requests
//
//***************************************************************

package org.example.neonarkintaketracker.controller;

import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.service.CreatureService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/creatures")
public class CreatureController {

    private final CreatureService service;

    public CreatureController(CreatureService service ){
        this.service = service;
    }

    //***************************************************************
    //
    //  Method:       getAllCreatures()
    //
    //  Description:  map http GET request at /api/creatures
    //  to this method
    //
    //  Parameters:   N/A
    //
    //  Returns:      200 if OK and a JSON file
    //
    //**************************************************************
    @GetMapping
    public ResponseEntity<List<Creature>> getAllCreatures(){
        List<Creature> creatures = service.getAllCreatures();
        return ResponseEntity.ok(creatures);
    }

    //***************************************************************
    //
    //  Method:       getCreatureById()
    //
    //  Description:  map http GET request at /api/creatures/{id}
    //  to this method
    //
    //  Parameters:   N/A
    //
    //  Returns:      200 if OK and a JSON file, 404 Not Found
    //  if fails
    //
    //**************************************************************
    @GetMapping("/{id}")
    public ResponseEntity<Creature> getCreatureById(@PathVariable Long id){

        Optional<Creature> maybeCreature = service.getCreatureById(id);

        // 404 Not Found
        if (maybeCreature.isEmpty()) return ResponseEntity.notFound().build();

        // 200 with a JSON
        return ResponseEntity.ok(maybeCreature.get());
    }

}
