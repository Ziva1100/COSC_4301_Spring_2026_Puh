package org.example.neonarkintaketracker.dto;

// This interface works with the listing all creatures with their habitats

// Capstone -- [ List All Creatures ] Menu Choice
public interface CreaturesSummaryRequest {
    Long getId();
    String getName();
    String getHabitat();
    String getSpecies();
    String getDangerLevel();
    String getCondition();
    Long getRemoved();

}
