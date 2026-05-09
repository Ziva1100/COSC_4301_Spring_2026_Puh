package org.example.neonarkclient.client.model;

// This file creates an observation model to be passed around
// between menu and service
// Capstone -- [ View Observations ] Menu Choice

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Observation {
    int id;
    String creature;
    LocalDate date;
    Category category;
    String observation;
}
