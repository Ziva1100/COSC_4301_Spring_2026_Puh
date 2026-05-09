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

import org.example.neonarkintaketracker.dto.CreatureRequest;
import org.example.neonarkintaketracker.dto.CreatureResponse;
import org.example.neonarkintaketracker.dto.CreaturesSummaryRequest;
import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.service.CreatureService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
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
//    @GetMapping
//    public ResponseEntity<List<Creature>> getAllCreatures(){
//        List<Creature> creatures = service.getAllCreatures();
//        return ResponseEntity.ok(creatures);
//    }

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
    // Capstone -- [ View Creature By Id ] Menu Choice
    @GetMapping("/{id}")
    public ResponseEntity<CreaturesSummaryRequest> getCreatureById(@PathVariable Long id){

        Optional<CreaturesSummaryRequest> maybeCreature = service.getCreatureById(id);

        // 404 Not Found
        if (maybeCreature.isEmpty()) return ResponseEntity.notFound().build();

        // 200 with a JSON
        return ResponseEntity.ok(maybeCreature.get());
    }

    //***************************************************************
    //
    //  Method:       create()
    //
    //  Description:  map client request to its newly created or
    //  updated entry via http request
    //
    //  Parameters:   CreatureRequest
    //
    //  Returns:      a new entry with a 201 created message
    //  or return 400 Bad Request in case of Validation failure
    //
    //**************************************************************
    @PostMapping
    public ResponseEntity<CreaturesSummaryRequest> create(@Valid @RequestBody CreatureRequest req){
        CreaturesSummaryRequest created = service.createCreature(req);

        // return 201 for success
        return ResponseEntity.status(201).body(created);
    }

    // Capstone -- [ List All Creatures ] Menu Choice
    @GetMapping()
    public ResponseEntity<List<CreaturesSummaryRequest>> getCreatureSummaries(){
        return ResponseEntity.ok(service.getAllCreatureSummary());
    }

    // Capstone -- [ rename creature] Menu Choice
    @PatchMapping("/{id}/name")
    public ResponseEntity<CreaturesSummaryRequest> rename(@PathVariable Long id, @RequestBody Map<String, String> body){
        return ResponseEntity.ok(service.renameCreature(id, body.get("name")));
    }


}
