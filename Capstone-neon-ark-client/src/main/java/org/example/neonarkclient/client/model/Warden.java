package org.example.neonarkclient.client.model;

//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #4
//
//  File Name:    Warden.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     5/14/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  The Warden object
//
//***************************************************************

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warden {

    // Create a warden object based on The previously defined database.
    // Ensure the separation of concern and hidden data
    // THe front end is not concern with the structure of the database
    // but rather with data only related to the warden

    /*(in_fname VARCHAR, in_id_num INT, in_id_type VARCHAR,
       in_email VARCHAR, in_role VARCHAR, in_emp_status VARCHAR, in_clearance VARCHAR,
       in_start_date DATE, in_lname VARCHAR DEFAULT NULL, in_end_date DATE DEFAULT NULL,
       in_dimension VARCHAR DEFAULT 'Earth')*/

    private String firstName;
    private String lastName;
    private int id;
    private IdType idType;
    private String email;
    private String role;
    private Status status;
    private Clearance clearance;
    private LocalDate startDate;
    private LocalDate endDate;
    private String dimension;



}
