package org.example.neonarkclient.client.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.neonarkclient.client.api.CapstoneApi;
import org.example.neonarkclient.client.exceptions.NotFoundException;
import org.example.neonarkclient.client.model.Creature;
import org.example.neonarkclient.client.model.Feeding;
import org.example.neonarkclient.client.model.Observation;
import org.example.neonarkclient.client.model.Warden;
import org.example.neonarkclient.client.service.CapstoneService;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// The main menu:
/*

=====================================
       NEON ARK CLI SYSTEM
=====================================

1. List all creatures
2. View creature by ID
3. Register new creature
4. Rename creature
5. Remove creature
6. View creature observations/notes
7. Find creatures by feeding time

--- Admin Only ---
8. View all system users

0. Exit
-------------------------------------
Select an option:

 */
public class CapstoneMenu
{
    private final Scanner scan = new Scanner(System.in);
    private final CapstoneService service;

    public CapstoneMenu(CapstoneService service){
        this.service = service;
    }

    public void run(){
        int userInput = 1;
        while(true) {

            System.out.println("=========================================================");
            System.out.println("        NEON ARK — CLI");
            System.out.println("=========================================================");
            display("");
            display("1. List All Creatures");
            display("2. View Creature By Id");
            display("3. Register New Creature");
            display("4. Rename Creature");
            display("5. Remove Creature");
            display("6. View Creature Observations/notes");
            display("7. Find Creatures By Feeding Time");
            display("");
            display("-- Admin Only --");
            display("8. View All System Users");
            display("");
            display("0. Exit");
            display("-------------------------------------");
            System.out.print("");

            // hanlde the possible non-int response by the user
            userInput = promptId("Select an option: ", "0");

            // pass the resposne to appropriate method
            switch (userInput) {
                case 1 -> listAllCreaturesMenu();
                case 2 -> viewCreatureByIdMenu();
                case 3 -> registerNewCreatureMenu();
                case 4 -> renameCreatureMenu();
                case 5 -> removeCreatureMenu();
                case 6 -> viewCreatureNotesMenu();
                case 7 -> creatureFeedingTimeMenu();
                case 8 -> viewUsersMenu();
                case 0 -> {
                    // double check if the user wants to exit
                    display("Write Y to exit: ");
                    String exitStr = scan.nextLine();
                    if (exitStr.equalsIgnoreCase("y"))
                        System.exit(0);

                }

                default -> display("The menu choice was incorrect. " +
                        "Please, try again!");
            }



        }
    }
    // Capstone -- [ List All Creatures ] Menu Choice
    // hanlde the option number one for listing all the creatures
    public void listAllCreaturesMenu(){
        List<Creature> creatures = new ArrayList<>();
        try {

            // handle all the exception passed on by the api client and service
            creatures =  service.listAllCreaturesSrv();
        }catch(JsonProcessingException e){
            display("An issue occurred.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // display the table
        String format = "%-5s %-15s %-20s %-18s %-10s %-13s %-8s%n";
        display("=".repeat(93));
        display(String.format(format, "ID", "NAME", "HABITAT", "SPECIES", "DANGER", "CONDITION", "REMOVED"));
        display("=".repeat(93));
        for (Creature c : creatures) {
            display(String.format(format,
                    c.getId(),
                    c.getName(),
                    c.getBiome(),
                    c.getSpecies(),
                    c.getDangerLevel(),
                    c.getCondition(),
                    c.getRemoved()
            ));
        }
        display("=".repeat(93));
        display("Total creatures: " + creatures.size());



    }

    // Capstone -- [ View Creature By Id ] Menu Choice
    public void viewCreatureByIdMenu(){
        int userInput = promptId("Enter the ID of the creature or press -1 to exit: ", "-1");
        Creature c = null;
        if (userInput == -1) return;
        try {

            // handle exceptions passed on by api and service
            c = service.viewCreatureByIdSrv(userInput);
        } catch (IOException e) {
            display("Un error occurred.");
        } catch (InterruptedException e) {
            display("An error occurred");
        } catch (NotFoundException e){
            display(e.getMessage());
        }

        // display the cresture if one is found, otherwise display the exception message
        if ((c != null)) {


            // display in a nice format
            String format = "%-5s %-15s %-20s %-18s %-10s %-13s %-8s%n";

            display("=".repeat(93));
            display(String.format(format, "ID", "NAME", "HABITAT", "SPECIES", "DANGER", "CONDITION", "REMOVED"));
            display("-".repeat(93));
            display(String.format(format,
                    c.getId(),
                    c.getName(),
                    c.getBiome(),
                    c.getSpecies(),
                    c.getDangerLevel(),
                    c.getCondition(),
                    c.getRemoved()
            ));
        } else{
            display(" ");
        }
    }

    // Capstone -- [ Register new creature] Menu Choice
    public void registerNewCreatureMenu(){

        boolean canceled = false;
        String name = "";
        String biome = "";
        String species = "";
        String dangerLevel = "";
        String condition = "";
        int removed = 0;

        display("Enter the creature properties or press -1 to come back to main menu.");

        // NAME
        while (!canceled) {
            name = promptString("Creature name: ");
            if (name.equals("-1")) { canceled = true; break; }
            try {
                name = service.validateCreatureName(name);
                break;
            } catch (IllegalArgumentException e) { display(e.getMessage()); }
        }

// BIOME
        while (!canceled) {
            display("Available biomes: FOREST, DESERT, OCEAN, AIR, OGRAVITY");
            biome = promptString("Habitat: ");
            if (biome.equals("-1")) { canceled = true; break; }
            try {
                biome = service.validateBiome(biome);
                break;
            } catch (IllegalArgumentException e) { display(e.getMessage()); }
        }

// SPECIES
        while (!canceled) {
            species = promptString("Species: ");
            if (species.equals("-1")) { canceled = true; break; }
            try {
                species = service.validateSpecies(species);
                break;
            } catch (IllegalArgumentException e) { display(e.getMessage()); }
        }

// DANGER LEVEL
        while (!canceled) {
            display("Available danger levels: LOW, MEDIUM, HIGH");
            dangerLevel = promptString("Danger level: ");
            if (dangerLevel.equals("-1")) { canceled = true; break; }
            try {
                dangerLevel = service.validateDangerLevel(dangerLevel);
                break;
            } catch (IllegalArgumentException e) { display(e.getMessage()); }
        }

// CONDITION
        while (!canceled) {
            display("Available conditions: STABLE, CRITICAL, QUARANTINED");
            condition = promptString("Condition: ");
            if (condition.equals("-1")) { canceled = true; break; }
            try {
                condition = service.validateCondition(condition);
                break;
            } catch (IllegalArgumentException e) { display(e.getMessage()); }
        }

        // build the creature for sending it to the service
        Creature newCreature = Creature.builder()
                .id(null)
                .name(name)
                .biome(biome)
                .species(species)
                .dangerLevel(dangerLevel)
                .condition(condition)
                .removed(0)
                .build();

        // process the response and catch possible exceptions
        Creature response = null;
        try {
            response = service.registerNewCreatureSrv(newCreature);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            display(e.getMessage());
        }
        if ((response != null)) {


            // display in a nice format
            String format = "%-5s %-15s %-20s %-18s %-10s %-13s %-8s%n";

            display("=".repeat(93));
            display(String.format(format, "ID", "NAME", "HABITAT", "SPECIES", "DANGER", "CONDITION", "REMOVED"));
            display("-".repeat(93));
            display(String.format(format,
                    response.getId(),
                    response.getName(),
                    response.getBiome(),
                    response.getSpecies(),
                    response.getDangerLevel(),
                    response.getCondition(),
                    response.getRemoved()
            ));
        } else{
            display(" ");
        }

    }

    // Capstone -- [ rename creature] Menu Choice
    public void renameCreatureMenu(){

        // first get the id of the creature to be renamed and validate that it exists
        int userInput = promptId("Enter the ID of the creature or press -1 to exit: ", "-1");

        Creature c = null;
        if (userInput == -1) return;
        try {

            // handle exceptions passed on by api and service
            c = service.viewCreatureByIdSrv(userInput);
        } catch (IOException e) {
            display("An error occurred.");
        } catch (InterruptedException e) {
            display("An error occurred");
        } catch (NotFoundException e){
            display(e.getMessage());
        }

        if (c != null){

            // if the service returns an actual creature, proceed with renaming
            String newName = promptString("Enter the creature's new name: ");
            String confirmation = promptString("Press Y to confirm renaming: ");


            // Confirm the user wants to make that change
            Creature newCreature;

            if(confirmation.equalsIgnoreCase("y")) {
                try {
                    newCreature = service.renameCreatureSrv(userInput, newName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // display the old and new name
                display("The creature "+c.getName()+" was renamed to "+newCreature.getName());
            }

        }

    }
    // Capstone -- [ Remove Creature ] Menu Choice
    public void removeCreatureMenu(){
        int id = promptId("Enter ID of press -1 to exit: ", "-1");
        Creature response = null;
        try {
            response = service.removeCreatureSrv(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e){
            display(e.getMessage());;
        }

        if ((response != null)) {


            // display in a nice format
            String format = "%-5s %-15s %-20s %-18s %-10s %-13s %-8s%n";

            display("=".repeat(93));
            display(String.format(format, "ID", "NAME", "HABITAT", "SPECIES", "DANGER", "CONDITION", "REMOVED"));
            display("-".repeat(93));
            display(String.format(format,
                    response.getId(),
                    response.getName(),
                    response.getBiome(),
                    response.getSpecies(),
                    response.getDangerLevel(),
                    response.getCondition(),
                    response.getRemoved()
            ));
        } else{
            display(" ");
        }





    }

    // Capstone -- [ View Observations ] Menu Choice
    public void viewCreatureNotesMenu(){

        List<Observation> observations = new ArrayList<>();

        int id = promptId("Enter the creature id or press -1 to exit: ", "-1");

        Creature creature = null;
        try {
            creature = service.viewCreatureByIdSrv(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NotFoundException e){
            display(e.getMessage());
        }

        if (creature != null) {

            try {
                observations = service.viewCreatureNotesSrv(id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            String format = "%-5s %-15s %-20s %-18s %-50s ";
            display("=".repeat(150));
            display(String.format(format, "ID", "CREATURE", "DATE", "CATEGORY", "OBSERVATION"));
            display("=".repeat(150));
            for (Observation o : observations) {
                display(String.format(format,
                        o.getId(),
                        o.getName(),
                        o.getDate(),
                        o.getCategory(),
                        o.getObservation()
                ));
            }
            display("=".repeat(93));
            display("Total observations: " + observations.size());
        }


    }
    // Capstone -- [ Find Creatures by feeding time ] Menu Choice
    public void creatureFeedingTimeMenu(){
        display("Enter the hour and minutes in a 24h format.");
        int hour = -1;
        int minutes = -1;

        // check the legality of the entries

        LocalTime time = null;
        while (true) {
            hour = promptId("Enter the hour as HH or press -1 to exit: ", "-1");
            if (hour == -1) return;
            try {
                hour = service.validateHour(hour);
            } catch (IllegalArgumentException e) {
                display(e.getMessage());
                continue;
            }
            minutes = promptId("Enter the minutes as MM or press -1 to exit: ","-1");
            if (minutes == -1) return;
            try {
                minutes = service.validateMinute(minutes);
                break;
            } catch (IllegalArgumentException e) {
                display(e.getMessage());
            }

        }

        time= LocalTime.of(hour, minutes);


        // execute the service
        List<Feeding> feedings = null;
        try {
            feedings = service.creatureFeedingTimeSrv(time);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(feedings.isEmpty()){
            display("\nThere are no feedings at the given time.\n");
        } else {
            String format = "%-20s %-30s %-10s %-15s%n";
            display("=".repeat(70));
            display(String.format(format, "CREATURE", "FOOD", "QUANTITY", "TIME"));
            display("=".repeat(70));
            for (Feeding f : feedings) {
                display(String.format(format,
                        f.getName(),
                        f.getFood(),
                        f.getQuantity(),
                        f.getTime()
                ));
            }
            display("=".repeat(65));
            display("Total feedings: " + feedings.size());
        }
    }

    // Capstone -- [ View All System Users ] Menu Choice
    public void viewUsersMenu(){

        // to get the informaiton, please sign in as
        // username: admin
        // password: admin123

        // accept the username and password
        String username = promptString("Enter your username: ");
        String password = promptString("Enter your password: ");

        List<Warden> wardens = new ArrayList<>();
        try{
            wardens = service.viewUsersSrv(username, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e){
            display(e.getMessage());
        }

        if(!wardens.isEmpty()) {
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
                        w.getClearance(),
                        w.getStartDate(),
                        w.getDimension());
            }

            System.out.println("-".repeat(145));
        }
    }


    // heper funciton to display somehting on the screen
    private void display(String str){
        System.out.println(str);
    }

    // helper method to prompt the user for an input
    private String promptString(String str){
        System.out.println(str);
        return scan.nextLine();

    }

    // helper funciton ensuring the integer can be parsed and doesn't exit the loop unti
    // and itneger is correctly parsed
    private int promptId(String message, String exitMessage) {
        while (true) {
            System.out.print(message);
            String input = scan.nextLine().trim();

            if (input.equals(exitMessage)) return Integer.parseInt(exitMessage);

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                display("The answer has to be a number. Try again or enter "+ exitMessage+" to exit.");
            }
        }
    }

}
