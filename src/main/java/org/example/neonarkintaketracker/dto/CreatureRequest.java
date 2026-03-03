//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #2
//
//  File Name:    CreatureRequest.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     3/28/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  The DTO record handling the requests for
//  creatures table
//
//***************************************************************

package org.example.neonarkintaketracker.dto;

// recieves data from the end user and sends it backend
public record CreatureRequest (
    String name,
    String species,
    String dangerLevel,
    String condition,
    Long habitatId,
    java.time.Instant createdAt
) {}
