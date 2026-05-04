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

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class WardenService {

    WardenApiClientInterface clientApi;

    public WardenService(WardenApiClientInterface clientApi) {
        this.clientApi = clientApi;
    }

    public void addNewWarden(
            String firstName,
            String lastName,
            String idNum,
            String idType,
            String email,
            String role,
            String status,
            String clearance,
            String startDate,
            String endDate,
            String dimension) throws IllegalAccessException {

        // Validate ID Number
        if (idNum.equals(""))
            throw new IllegalAccessException("The ID Number cannot be empty");
        int idNumber = null;
        try {
            idNumber = Integer.parseInt(idNum);
        } catch (NumberFormatException e) {
            System.out.println("ID number invalid");
        }

        // Validate first name
        if (firstName.equals(""))
            throw new IllegalAccessException("First name cannot be empty");

        // Validate ID type
        if (idType.equals(""))
            throw new IllegalAccessException("The ID cannot be empty");

        IdType typeId = null;
        try {
            typeId = IdType.valueOf(idType.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            System.out.println("The ID type not allowed");
        }

        // validate role
        if (role.equals(""))
            throw new IllegalAccessException("The role cannot be empty");


        // validate status
        if (status.equals(""))
            throw new IllegalAccessException("The status cannot be empty");

        Status enumStatus = null;
        try {
            enumStatus = Status.valueOf(status.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            System.out.println("The status type not allowed");
        }

        // validate clearance
        if (clearance.equals(""))
            throw new IllegalAccessException("The clearance cannot be empty");

        Clearance clearanceEnum = null;
        try {
            clearanceEnum = Clearance.valueOf(clearance.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            System.out.println("The clearance type not allowed");
        }

        // validate start date
        if (startDate.equals(""))
            throw new IllegalAccessException("The start date cannot be empty");

        LocalDate startingDate = null;
        try {
            startingDate = LocalDate.parse(startDate);
        } catch (DateTimeParseException e) {
            System.out.println("The date is in the wrong format");
        }

        // validate end date
        LocalDate endingDate = null;
        if (!endDate.equals("")) {
            try {
                endingDate = LocalDate.parse(endDate);

            } catch (DateTimeParseException e) {
                System.out.println("The date is in the wrong format");
            }


        }

        // validate dimension
        if (dimension.equals(""))
            throw new IllegalAccessException("The dimension cannot be empty");

        Warden warden = Warden.builder()
                .firstName(firstName)
                .lastName(lastName)
                .id(idNumber)
                .idType(typeId)
                .email(email)
                .role(role)
                .status(enumStatus)
                .clearance(clearanceEnum)
                .startDate(startingDate)
                .endDate(endingDate)
                .dimension(dimension)
                .build();

        clientApi.saveNewWarden(warden);

    }
}
