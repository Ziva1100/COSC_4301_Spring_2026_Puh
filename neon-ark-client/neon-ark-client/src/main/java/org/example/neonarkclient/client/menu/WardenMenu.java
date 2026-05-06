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
import java.util.*;

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

        Warden created = null;
        try {
            created = service.addNewWarden(warden);
        } catch (IOException e) {
            display("There was an issue looking for a duplicate warden");
        }
        if (created == null) {
            display("This warden already exists");
        } else {
            display(

                    "============================================================\n" +
                            "ACTION: Add New Warden\n" +
                            "============================================================\n" +
                            "Inputs Requested:  firstName, lastName, id, idType, email, " +
                            "role, status, clearance, startDate, endDate, dimensions\n" +
                            "\n" +
                            "Description\n" +
                            "Add a new warden with all the needed information. " +
                            "The backend server takes care of appropriate saving in the " +
                            "database." +
                            "\n" +
                            "WOULD SEND: POST /api/wardens/register\n" +
                            "Payload:\n" +
                            "  {\n" +
                            "    \"id\"          : " + warden.getId() + ",\n" +
                            "    \"firstName\"   : \"" + warden.getFirstName() + "\",\n" +
                            "    \"lastName\"    : \"" + warden.getLastName() + "\",\n" +
                            "    \"idType\"      : \"" + warden.getIdType() + "\",\n" +
                            "    \"email\"       : \"" + warden.getEmail() + "\",\n" +
                            "    \"role\"        : \"" + warden.getRole() + "\",\n" +
                            "    \"status\"      : \"" + warden.getStatus() + "\",\n" +
                            "    \"clearance\"   : \"" + warden.getClearance() + "\",\n" +
                            "    \"startDate\"   : \"" + warden.getStartDate() + "\",\n" +
                            "    \"endDate\"     : \"" + warden.getEndDate() + "\",\n" +
                            "    \"dimension\"   : \"" + warden.getDimension() + "\"\n" +
                            "  }\n" +
                            "\n" +
                            "Result: SUCCESS (simulated)\n" +
                            "============================================================"


            );
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
        int userInput = 0;

        display("---------------------------------------------------------\n" +
                        "[ 3 ] Update Warden\n" +
                        "---------------------------------------------------------\n" +
                        "Required:\n" +
                        "• Enter warden ID\n" +
                        "\n" +
                        "1. Update Role\n" +
                        "2. Update Clearance Level\n" +
                        "3. Update Employment Status\n" +
                        "4. Update Start Date\n" +
                        "5. Update End Date (Termination Date)\n" +
                        "6. Update Email\n" +
                        "7. Cancel\n");

        try {
            userInput = scan.nextInt();
            scan.nextLine();
        }catch (InputMismatchException e) {
            display("The answer has to be a number!");
            scan.nextInt();
        }

        switch (userInput) {
            case 1 -> updateRoleMenu();
            case 2 -> updateClearanceMenu();
            case 3 -> updateEmploymentStatusMenu();
            case 4 -> updateStartDateMenu();
            case 5 -> updateEndDateMenu();
            case 6 -> updateEmailMenu();
            case 7 -> {
                return;
            }
            default -> display("The menu choice was incorrect. " +
                    "Please, try again!");
        }

        scan.nextInt();
    }
    private void viewWardensMenu(){
        int userInput = 0;
        while(userInput != 5 ) {

            display("---------------------------------------------------------\n" +
                    "[ 2 ] View Wardens\n" +
                    "---------------------------------------------------------\n" +
                    "1. View All Wardens\n" +
                    "2. View Warden by ID\n" +
                    "3. View Wardens by Employment Status\n" +
                    "4. View Wardens by Role\n" +
                    "5. Return to MAIN MENU\n");

            try {
                userInput = scan.nextInt();
                scan.nextLine();
            }catch (InputMismatchException e) {
                display("The answer has to be a number!");
                scan.nextInt();
            }

            switch (userInput) {
                case 1 -> viewAllWardensMenu();
                case 2 -> viewWardenByIdMenu();
                case 3 -> viewWardenByEmpMenu();
                case 4 -> viewWardenByRoleMenu();
                case 5 -> {
                    return;
                }
                default -> display("The menu choice was incorrect. " +
                        "Please, try again!");
            }

            scan.nextInt();

        }
    }
    private void updateRoleMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: Update Warden Role\n" +
                        "============================================================\n" +
                        "Inputs Required  : wardenId, newRole\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - wardenId must be a valid integer\n" +
                        "  - wardenId must exist in the system\n" +
                        "  - newRole must be a non-empty string\n" +
                        "  - newRole must match an existing role in the system\n" +
                        "\n" +
                        "WOULD SEND: PUT /api/wardens/{id}/role\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> Warden role updated successfully.\n" +
                        "============================================================";

        display(response);
    }

    private void updateClearanceMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: Update Warden Clearance Level\n" +
                        "============================================================\n" +
                        "Inputs Required  : wardenId, newClearance\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - wardenId must be a valid integer\n" +
                        "  - wardenId must exist in the system\n" +
                        "  - newClearance must match a valid enum value\n" +
                        "  - accepted values: ALPHA, ECLIPSE, OMEGA\n" +
                        "\n" +
                        "WOULD SEND: PUT /api/wardens/{id}/clearance\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> Warden clearance level updated successfully.\n" +
                        "============================================================";

        display(response);
    }

    private void updateEmploymentStatusMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: Update Warden Employment Status\n" +
                        "============================================================\n" +
                        "Inputs Required  : wardenId, newStatus\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - wardenId must be a valid integer\n" +
                        "  - wardenId must exist in the system\n" +
                        "  - newStatus must match a valid enum value\n" +
                        "  - accepted values: ACTIVE, TERMINATED, ON_LEAVE\n" +
                        "\n" +
                        "WOULD SEND: PUT /api/wardens/{id}/status\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> Warden employment status updated successfully.\n" +
                        "============================================================";

        display(response);
    }

    private void updateStartDateMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: Update Warden Start Date\n" +
                        "============================================================\n" +
                        "Inputs Required  : wardenId, newStartDate\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - wardenId must be a valid integer\n" +
                        "  - wardenId must exist in the system\n" +
                        "  - newStartDate must be in format YYYY-MM-DD\n" +
                        "  - newStartDate must not be after end date if end date exists\n" +
                        "\n" +
                        "WOULD SEND: PUT /api/wardens/{id}/startDate\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> Warden start date updated successfully.\n" +
                        "============================================================";

        display(response);
    }

    private void updateEndDateMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: Update Warden End Date (Termination Date)\n" +
                        "============================================================\n" +
                        "Inputs Required  : wardenId, newEndDate\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - wardenId must be a valid integer\n" +
                        "  - wardenId must exist in the system\n" +
                        "  - newEndDate must be in format YYYY-MM-DD\n" +
                        "  - newEndDate must not be before start date\n" +
                        "\n" +
                        "WOULD SEND: PUT /api/wardens/{id}/endDate\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> Warden termination date updated successfully.\n" +
                        "============================================================";

        display(response);
    }

    private void updateEmailMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: Update Warden Email\n" +
                        "============================================================\n" +
                        "Inputs Required  : wardenId, newEmail\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - wardenId must be a valid integer\n" +
                        "  - wardenId must exist in the system\n" +
                        "  - newEmail must be a valid email format\n" +
                        "  - newEmail must not already exist in the system\n" +
                        "\n" +
                        "WOULD SEND: PUT /api/wardens/{id}/email\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> Warden email updated successfully.\n" +
                        "============================================================";

        display(response);
    }
    private void viewWardenByIdMenu(){
        String response =
                "============================================================\n" +
                        "ACTION: View Warden by ID\n" +
                        "============================================================\n" +
                        "Inputs Required  : wardenId\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - wardenId must be a valid integer\n" +
                        "  - wardenId must exist in the system\n" +
                        "\n" +
                        "WOULD SEND: GET /api/wardens/{id}\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> Warden record located and returned.\n" +
                        "============================================================";

        display(response);
    }

    private void viewWardenByEmpMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: View Wardens by Employment Status\n" +
                        "============================================================\n" +
                        "Inputs Required  : status\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - status must match a valid enum value\n" +
                        "  - accepted values: ACTIVE, TERMINATED, ONLEAVE\n" +
                        "\n" +
                        "WOULD SEND: GET /api/wardens?status={status}\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> All wardens matching status returned.\n" +
                        "============================================================";

        display(response);
    }

    private void viewWardenByRoleMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: View Wardens by Role\n" +
                        "============================================================\n" +
                        "Inputs Required  : role\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - role must be a non-empty string\n" +
                        "  - role must match an existing role in the system\n" +
                        "\n" +
                        "WOULD SEND: GET /api/wardens?role={role}\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> All wardens matching role returned.\n" +
                        "============================================================";

        display(response);
    }


    private void viewAllWardensMenu(){
        List<Warden> wardens = new ArrayList<>();
        try{

            wardens = service.viewAllWardens();
        }catch(IOException e){
            display("There was an issue retriving all wardens.");
        }
        if (wardens == null) {
            display("There was an issue returning the wardens.");
        } else {
            display("============================================================\n" +
                    "ACTION: Fetch All Wardens\n" +
                    "============================================================\n" +
                    "SENT: GET /api/wardens\n" +
                    "Description: Retrieves all warden records from the intake manifest.\n" +
                    "Result: SUCCESS (simulated)\n" +
                    "Total Records Returned: " + wardens.size() + "\n" +
                    "============================================================\n"
            );
            System.out.printf("%-6s %-12s %-12s %-10s %-28s %-15s %-12s %-10s %-12s %-12s %-10s%n",
                    "ID", "FIRST NAME", "LAST NAME", "ID TYPE", "EMAIL", "ROLE",
                    "STATUS", "CLEARANCE", "START DATE", "END DATE", "DIMENSION");

            // divider
            System.out.println("-".repeat(145));

            // rows
            for (Warden w : wardens) {
                System.out.printf("%-6d %-12s %-12s %-10s %-28s %-15s %-12s %-10s %-12s %-12s %-10s%n",
                        w.getId(),
                        w.getFirstName(),
                        w.getLastName(),
                        w.getIdType(),
                        w.getEmail(),
                        w.getRole(),
                        w.getStatus(),
                        w.getClearance(),
                        w.getStartDate(),
                        w.getEndDate() == null ? "N/A" : w.getEndDate(),
                        w.getDimension());
            }

            System.out.println("-".repeat(145));
            display("Press 1 to return to the main menu.");
        }


    }

    private void manageCertificatesMenu(){
        int userInput = 0;
        display("---------------------------------------------------------\n" +
                "[ 4 ] Manage Certifications\n" +
                "---------------------------------------------------------\n" +
                "Required:\n" +
                "• Enter warden ID\n" +
                "\n" +
                "1. Add Certification\n" +
                "2. View Certifications\n" +
                "3. Mark Certification Expired\n" +
                "4. Remove Certification\n" +
                "5. Return to MAIN MENU\n");

        try {
            userInput = scan.nextInt();
            scan.nextLine();
        }catch (InputMismatchException e) {
            display("The answer has to be a number!");
            scan.nextInt();
        }

        switch (userInput) {
            case 1 -> addCertificationMenu();
            case 2 -> viewCertificationsMenu();
            case 3 -> markCertificationExpiredMenu();
            case 4 -> removeCertificationMenu();
            case 5 -> {
                return;
            }
            default -> display("The menu choice was incorrect. " +
                    "Please, try again!");
        }


    }

    private void addCertificationMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: Add Certification\n" +
                        "============================================================\n" +
                        "Inputs Required  : wardenId, certificationName, issueDate, expirationDate\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - wardenId must be a valid integer\n" +
                        "  - wardenId must exist in the system\n" +
                        "  - certificationName must be a non-empty string\n" +
                        "  - issueDate must be in format YYYY-MM-DD\n" +
                        "  - expirationDate must be after issueDate\n" +
                        "\n" +
                        "WOULD SEND: POST /api/wardens/{id}/certifications\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> Certification added to warden record successfully.\n" +
                        "============================================================";

        display(response);
    }

    private void viewCertificationsMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: View Certifications\n" +
                        "============================================================\n" +
                        "Inputs Required  : wardenId\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - wardenId must be a valid integer\n" +
                        "  - wardenId must exist in the system\n" +
                        "\n" +
                        "WOULD SEND: GET /api/wardens/{id}/certifications\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> All certifications for warden returned.\n" +
                        "============================================================";

        display(response);
    }

    private void markCertificationExpiredMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: Mark Certification Expired\n" +
                        "============================================================\n" +
                        "Inputs Required  : wardenId, certificationId\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - wardenId must be a valid integer\n" +
                        "  - wardenId must exist in the system\n" +
                        "  - certificationId must be a valid integer\n" +
                        "  - certificationId must belong to the specified warden\n" +
                        "  - certification must not already be marked expired\n" +
                        "\n" +
                        "WOULD SEND: PUT /api/wardens/{id}/certifications/{certId}/expire\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> Certification marked as expired successfully.\n" +
                        "============================================================";

        display(response);
    }

    private void removeCertificationMenu() {
        String response =
                "============================================================\n" +
                        "ACTION: Remove Certification\n" +
                        "============================================================\n" +
                        "Inputs Required  : wardenId, certificationId\n" +
                        "\n" +
                        "Validation:\n" +
                        "  - wardenId must be a valid integer\n" +
                        "  - wardenId must exist in the system\n" +
                        "  - certificationId must be a valid integer\n" +
                        "  - certificationId must belong to the specified warden\n" +
                        "\n" +
                        "WOULD SEND: DELETE /api/wardens/{id}/certifications/{certId}\n" +
                        "\n" +
                        "Result: SUCCESS (simulated)\n" +
                        "  >> Certification removed from warden record successfully.\n" +
                        "============================================================";

        display(response);
    }

    private void deactWardenMenu(){

    }

}
