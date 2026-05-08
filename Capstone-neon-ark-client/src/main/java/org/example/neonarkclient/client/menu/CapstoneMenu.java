package org.example.neonarkclient.client.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.neonarkclient.client.exceptions.NotFoundException;
import org.example.neonarkclient.client.model.Creature;
import org.example.neonarkclient.client.service.CapstoneService;

import java.io.IOException;
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
            System.out.print("Select an option: ");

            // hanlde the possible non-int response by the user
            try {
                userInput = scan.nextInt();
                scan.nextLine();
            }catch (InputMismatchException e) {
                display("The answer has to be a number!");
                scan.nextInt();
            }

            // pass the resposne to appropriate method
            switch (userInput) {
                case 1 -> listAllCreaturesMenu();
                case 2 -> viewCreatureByIdMenu();
                case 3 -> registerNewCreatureMenu();
                case 4 -> renameCreatureMenu();
                case 5 -> viewCreatureNotesMenu();
                case 6 -> creatureFeedingTimeMenu();
                case 7 -> viewUsersMenu();
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
        display("Enter the ID of the creature or press -1 to exit: ");
        int userInput = 0;
        try {

            // ensure the input is in the int format
            userInput = scan.nextInt();
            scan.nextLine();
        }catch (InputMismatchException e) {
            display("The answer has to be a number!");
            scan.nextInt();
        }
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

        Creature newCreature = Creature.builder()
                .id(null)
                .name(name)
                .biome(biome)
                .species(species)
                .dangerLevel(dangerLevel)
                .condition(condition)
                .removed(0)
                .build();

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

    public void renameCreatureMenu(){
        service.renameCreatureSrv();
    }

    public void viewCreatureNotesMenu(){
        service.viewCreatureNotesSrv();
    }

    public void creatureFeedingTimeMenu(){
        service.creatureFeedingTimeSrv();
    }

    public void viewUsersMenu(){
        service.viewUsersSrv();
    }


    private void display(String str){
        System.out.println(str);
    }

    private String promptString(String str){
        System.out.println(str);
        return scan.nextLine();

    }

}
