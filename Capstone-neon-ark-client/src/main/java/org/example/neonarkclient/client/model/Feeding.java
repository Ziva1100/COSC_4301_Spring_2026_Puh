package org.example.neonarkclient.client.model;

// Capstone -- [ Find Creatures by feeding time ] Menu Choice
// a model to pass between menu and service

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feeding {
    String name;
    String food;
    String quantity;
    LocalTime time;
}
