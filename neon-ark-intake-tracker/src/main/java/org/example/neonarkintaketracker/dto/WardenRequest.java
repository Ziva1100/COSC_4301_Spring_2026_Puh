package org.example.neonarkintaketracker.dto;

import java.time.LocalDate;

// Capstone -- [ View All System Users ] Menu Choice
// Create a warden request DTO that matches the front end warden model

public record WardenRequest(
        String firstName,
        String lastName,
        int id,
        String idType,
        String email,
        String role,
        String clearance,
        LocalDate startDate,
        String dimension
) {}