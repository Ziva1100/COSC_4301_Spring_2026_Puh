//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #4
//
//  File Name:    WardenService.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     5/14/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  Handles business logic of the menu choices
//
//***************************************************************

package org.example.neonarkclient.client.service;

import org.example.neonarkclient.client.api.WardenApiClientInterface;
import org.example.neonarkclient.client.model.Clearance;
import org.example.neonarkclient.client.model.IdType;
import org.example.neonarkclient.client.model.Status;
import org.example.neonarkclient.client.model.Warden;

import javax.management.relation.Role;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class WardenService {

    WardenApiClientInterface clientApi;

    public WardenService(WardenApiClientInterface clientApi) {
        this.clientApi = clientApi;
    }

    //***************************************************************
    //
    //  Method:       addNewWarden() and supporting classes
    //
    //  Description:  validates the data inserted from the menu class
    // and passes it to the API to add a new warden
    //
    //  Parameters:   warden
    //
    //  Returns:      Warden
    //
    //**************************************************************
    public String validateFirstName(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("First name cannot be blank.");
        return input.trim();
    }

    public String validateLastName(String input) {
        // optional — blank is allowed
        return input == null ? "" : input.trim();
    }

    public int validateIdNumber(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("ID number cannot be blank.");
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID number must be a valid number.");
        }
    }

    public IdType validateIdType(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("ID type cannot be blank.");
        try {
            return IdType.valueOf(input.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ID type. Options: "
                    + Arrays.toString(IdType.values()));
        }
    }

    public String validateEmail(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("Email cannot be blank.");
        return input.trim();
    }

    public String validateRole(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("Role cannot be blank.");
        return input.trim();

    }

    public Status validateStatus(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("Status cannot be blank.");
        try {
            return Status.valueOf(input.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status. Options: "
                    + Arrays.toString(Status.values()));
        }
    }

    public Clearance validateClearance(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("Clearance cannot be blank.");
        try {
            return Clearance.valueOf(input.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid clearance. Options: "
                    + Arrays.toString(Clearance.values()));
        }
    }

    // validates parsing succeeds, returns the cleaned string
    public String validateStartDate(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("Start date cannot be blank.");
        try {
            LocalDate.parse(input.trim());   // just checks it parses — result discarded
            return input.trim();             // return the string if parsing passed
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Start date must be yyyy-MM-dd format. Example: 2024-03-15");
        }
    }

    // optional — blank returns empty string, otherwise validates and returns string
    public String validateEndDate(String input, String startDate) {
        if (input == null || input.isBlank()) return "";
        try {
            LocalDate end   = LocalDate.parse(input.trim());
            LocalDate start = LocalDate.parse(startDate.trim());
            if (end.isBefore(start))
                throw new IllegalArgumentException(
                        "End date cannot be before start date (" + startDate + ").");
            return input.trim();             // return the string if all checks passed
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "End date must be yyyy-MM-dd format. Example: 2024-03-15");
        }
    }

    public String validateDimension(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("Dimension cannot be blank.");
        return input.trim();
    }

    public String addNewWarden(Warden warden) throws IOException {

        String duplicate = clientApi.getWardenById(warden.getId());


        return duplicate.isEmpty() ? System.out.println(clientApi.addNewWarden(warden)) : "";
    }
}