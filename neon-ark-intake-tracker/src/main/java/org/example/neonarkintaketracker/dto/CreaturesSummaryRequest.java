package org.example.neonarkintaketracker.dto;

// This record works with the listing all creatures with their habitats


// Capstone -- [ List All Creatures ] Menu Choice
// Capstone -- [ View Creature By Id ] Menu Choice
// Capstone -- [ Register New Creature ] Menu Choice
public record CreaturesSummaryRequest (

    Long id,
    String name,
    String biome,
    String species,
    String dangerLevel,
    String condition,

    int removed)
        {}
