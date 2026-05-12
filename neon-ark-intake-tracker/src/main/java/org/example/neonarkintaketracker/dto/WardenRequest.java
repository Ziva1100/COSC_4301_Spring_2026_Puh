package org.example.neonarkintaketracker.dto;

import java.time.LocalDate;

// Capstone -- [ View All System Users ] Menu Choice
// Create a warden request DTO that matches the front end warden model

public record WardenRequest(
        int wardenId,
        String firstName,
        String lastName,
        int alternateId,
        String idType,
        String email,
        String role,
        String clearance,
        String startDate,
        String dimension
) {}