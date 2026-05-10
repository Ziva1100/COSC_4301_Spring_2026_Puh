package org.example.neonarkintaketracker.dto;

import java.time.LocalTime;

// this is a DTO record to send to the front end
// Capstone -- [ Find Creatures by feeding time ] Menu Choice
public record FeedingRequest(
        String name,
        String food,
        String quantity,
        LocalTime time
) {
}
