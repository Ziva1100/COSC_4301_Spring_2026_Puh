package org.example.neonarkintaketracker.dto;

// This record works with the listing all creatures with their habitats


// Capstone -- [ List All Creatures ] Menu Choice


public record CreaturesSummaryRequest (

    Long id,
    String name,
    String biome,
    String species,
    String dangerLevel,
    String condition

)
        {}
