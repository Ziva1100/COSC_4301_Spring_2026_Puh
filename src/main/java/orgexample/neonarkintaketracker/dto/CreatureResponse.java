//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #2
//
//  File Name:    CreatureResponse.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     3/28/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  The DTO record handling the response for
//  creatures table
//
//***************************************************************

package orgexample.neonarkintaketracker.dto;

public record CreatureResponse(
    Long id,
    String name,
    String species,
    String dangerLevel,
    String condition,
    java.time.Instant createdAt
) {
}
