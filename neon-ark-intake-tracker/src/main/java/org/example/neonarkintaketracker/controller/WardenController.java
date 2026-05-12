package org.example.neonarkintaketracker.controller;

// Capstone -- [ View All System Users ] Menu Choice
// this controller will communicate with the front end api to return the wardens list

import org.example.neonarkintaketracker.dto.WardenRequest;
import org.example.neonarkintaketracker.exception.ForbiddenException;
import org.example.neonarkintaketracker.exception.UnauthorizedException;
import org.example.neonarkintaketracker.service.WardenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wardens")
public class WardenController {

    private final WardenService service;

    public WardenController(WardenService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<WardenRequest>> viewAllUsers(@RequestParam String username,
                                                            @RequestParam String password){
        try {
            return ResponseEntity.ok(service.getAllUsers(username, password));

        } catch (UnauthorizedException e){
            return ResponseEntity.status(401).build();
        } catch (ForbiddenException e){
            return ResponseEntity.status(403).build();
        }

    }
}
