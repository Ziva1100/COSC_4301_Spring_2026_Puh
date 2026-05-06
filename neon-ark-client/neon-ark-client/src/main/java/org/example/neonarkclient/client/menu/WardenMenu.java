//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #4
//
//  File Name:    WardenMenu.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     5/14/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  gets user input and redirects it to
//  the appropriate service action
//
//***************************************************************

package org.example.neonarkclient.client.menu;

import org.example.neonarkclient.client.model.Clearance;
import org.example.neonarkclient.client.model.IdType;
import org.example.neonarkclient.client.model.Status;
import org.example.neonarkclient.client.model.Warden;
import org.example.neonarkclient.client.service.WardenService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WardenMenu {

    private final Scanner scan = new Scanner(System.in);
    private final WardenService service;

    public WardenMenu(WardenService service){
        this.service = service;
    }

    public void run(){
        int userInput = 0;

        while(userInput != 6) {

            System.out.println("=========================================================");
            System.out.println("        NEON ARK — ADMIN WARDEN ONBOARDING CONSOLE");
            System.out.println("=========================================================");
            System.out.println();
            System.out.println("[ MAIN MENU ]");
            System.out.println("---------------------------------------------------------");
            display("1. Add New Warden");
            display("2. View Wardens");
            display("3. Update Warden");
            display("4. Manage Certifications");
            display("5. Deactivate / Terminate Warden");
            display("6. Exit");

            try {
                userInput = scan.nextInt();
                scan.nextLine();
            }catch (InputMismatchException e) {
                display("The answer has to be a number!");
                scan.nextInt();
            }

            switch (userInput) {
                case 1 -> addNewWardenMenu();
                case 2 -> viewWardensMenu();
                case 3 -> updateWardenMenu();
                case 4 -> manageCertificatesMenu();
                case 5 -> deactWardenMenu();
                case 6 -> {
                    display("Thank you for using Neon Ark Tracker!");
                    System.exit(0);
                }
                default -> display("The menu choice was incorrect. " +
                        "Please, try again!");
            }

            scan.nextInt();


        }

    }

    //***************************************************************
    //
    //  Method:       addNewWardenMenu()
    //
    //  Description:  runs the menu option of adding the warden
    //  and passes the input to warden service
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //**************************************************************
    private void addNewWardenMenu() {
        display("[ Add New Warden ]");
        display("Type -1 at any prompt to return to the main menu.");
        display("---------------------------------------------------------");

        boolean canceled = false;
        String firstName = "";
        String lastName  = "";
        String idNum     = "";
        String idType    = "";
        String email     = "";
        String role      = "";
        String status    = "";
        String clearance = "";
        String startDate = "";
        String endDate   = "";
        String dimension = "";

        // first name
        while (!canceled) {
            firstName = promptString("First name: ");
            if (firstName.equals("-1")) {
                canceled = true;
            } else {
                try {
                    firstName = service.validateFirstName(firstName);
                    break;
                } catch (IllegalArgumentException e) {
                    display(e.getMessage());
                }
            }
        }

        // last name — optional, blank is allowed
        while (!canceled) {
            lastName = promptString("Last name [optional]: ");
            if (lastName.equals("-1")) {
                canceled = true;
            } else {
                lastName = service.validateLastName(lastName);
                break;  // no validation exception possible — always breaks
            }
        }

        // ID number
        while (!canceled) {
            idNum = promptString("ID number: ");
            if (idNum.equals("-1")) {
                canceled = true;
            } else {
                try {
                    service.validateIdNumber(idNum);
                    break;
                } catch (IllegalArgumentException e) {
                    display(e.getMessage());
                }
            }
        }

        // ID type
        while (!canceled) {
            idType = promptString("ID type [BADGE, VISA, PASSPORT]: ");
            if (idType.equals("-1")) {
                canceled = true;
            } else {
                try {
                    service.validateIdType(idType);
                    idType = idType.toUpperCase().trim();
                    break;
                } catch (IllegalArgumentException e) {
                    display(e.getMessage());
                }
            }
        }

        // email
        while (!canceled) {
            email = promptString("Email: ");
            if (email.equals("-1")) {
                canceled = true;
            } else {
                try {
                    email = service.validateEmail(email);
                    break;
                } catch (IllegalArgumentException e) {
                    display(e.getMessage());
                }
            }
        }

        // role
        while (!canceled) {
            role = promptString("Role: ");
            if (role.equals("-1")) {
                canceled = true;
            } else {
                try {
                    service.validateRole(role);
                    role = role.toUpperCase().trim();
                    break;
                } catch (IllegalArgumentException e) {
                    display(e.getMessage());
                }
            }
        }

        // status
        while (!canceled) {
            status = promptString("Status " + Arrays.toString(Status.values()) + ": ");
            if (status.equals("-1")) {
                canceled = true;
            } else {
                try {
                    service.validateStatus(status);
                    status = status.toUpperCase().trim();
                    break;
                } catch (IllegalArgumentException e) {
                    display(e.getMessage());
                }
            }
        }

        // clearance
        while (!canceled) {
            clearance = promptString("Clearance " + Arrays.toString(Clearance.values()) + ": ");
            if (clearance.equals("-1")) {
                canceled = true;
            } else {
                try {
                    service.validateClearance(clearance);
                    clearance = clearance.toUpperCase().trim();
                    break;
                } catch (IllegalArgumentException e) {
                    display(e.getMessage());
                }
            }
        }

        // start date
        while (!canceled) {
            startDate = promptString("Starting day [yyyy-MM-dd]: ");
            if (startDate.equals("-1")) {
                canceled = true;
            } else {
                try {
                    service.validateStartDate(startDate);
                    break;
                } catch (IllegalArgumentException e) {
                    display(e.getMessage());
                }
            }
        }

        // end date — optional, blank is allowed
        while (!canceled) {
            endDate = promptString("Ending day [yyyy-MM-dd] [optional]: ");
            if (endDate.equals("-1")) {
                canceled = true;
            } else {
                if (endDate.isBlank()) {
                    break;  // optional — skip validation
                }
                try {
                    service.validateEndDate(endDate, startDate);
                    break;
                } catch (IllegalArgumentException e) {
                    display(e.getMessage());
                }
            }
        }

        // dimension
        while (!canceled) {
            dimension = promptString("Dimension: ");
            if (dimension.equals("-1")) {
                canceled = true;
            } else {
                try {
                    dimension = service.validateDimension(dimension);
                    break;
                } catch (IllegalArgumentException e) {
                    display(e.getMessage());
                }
            }
        }

        // only build if user didn't cancel
        if (canceled) {
            display("Press 1 to return to the main menu.");
            return;
        }

        Warden warden = Warden.builder()
                .firstName(firstName)
                .lastName(lastName)
                .id(Integer.parseInt(idNum))
                .idType(IdType.valueOf(idType))
                .email(email)
                .role(role)
                .status(Status.valueOf(status))
                .clearance(Clearance.valueOf(clearance))
                .startDate(LocalDate.parse(startDate))
                .endDate(endDate.isBlank() ? null : LocalDate.parse(endDate))
                .dimension(dimension)
                .build();

        String created = null;
        try {
            created = service.addNewWarden(warden);
        } catch (IOException e) {
            display("There was an issue looking for a duplicate warden");
        }
        if (created.isEmpty()) {
            display("This warden already exists");
        } else {
            display(created);
        }
        display("Press 1 to return to the main menu.");
    }

    private void display(String str){
        System.out.println(str);
    }

    private String promptString(String str){
        System.out.println(str);
        return scan.nextLine();

    }


    private void updateWardenMenu(){

    }

    private void viewWardensMenu(){

    }

    private void manageCertificatesMenu(){

    }

    private void deactWardenMenu(){

    }

}
