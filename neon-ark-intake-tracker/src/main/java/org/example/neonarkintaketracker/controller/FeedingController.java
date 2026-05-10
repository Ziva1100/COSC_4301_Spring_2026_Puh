package org.example.neonarkintaketracker.controller;

// this is a class that controls the communication between the
// front end server and the backend server for feedings

import org.example.neonarkintaketracker.dto.FeedingRequest;
import org.example.neonarkintaketracker.service.FeedingService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedings")

// Capstone -- [ Find Creatures by feeding time ] Menu Choice
public class FeedingController {

    private final FeedingService service;

    public FeedingController(FeedingService service){
        this.service = service;
    }

    @GetMapping("/time")
    ResponseEntity<List<FeedingRequest>> getFeedingTimes(@RequestParam("time") String time)
{
        List<FeedingRequest> maybeFeeding = service.getFeedingTimesSrv(time);


        return ResponseEntity.ok(maybeFeeding.get());

    }

}
