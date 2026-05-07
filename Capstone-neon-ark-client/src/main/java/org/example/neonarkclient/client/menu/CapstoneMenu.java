package org.example.neonarkclient.client.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.neonarkclient.client.model.Creature;
import org.example.neonarkclient.client.service.CapstoneService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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

            try {
                userInput = scan.nextInt();
                scan.nextLine();
            }catch (InputMismatchException e) {
                display("The answer has to be a number!");
                scan.nextInt();
            }

            switch (userInput) {
                case 1 -> listAllCreaturesMenu();
                case 2 -> viewCreatureByIdMenu();
                case 3 -> registerNewCreatureMenu();
                case 4 -> renameCreatureMenu();
                case 5 -> viewCreatureNotesMenu();
                case 6 -> creatureFeedingTimeMenu();
                case 7 -> viewUsersMenu();
                case 0 -> {
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
    public void listAllCreaturesMenu(){
        List<Creature> creatures = new ArrayList<>();
        try {
            creatures =  service.listAllCreaturesSrv();
        }catch(JsonProcessingException e){
            display("An issue occurred.");
        }

            String format = "%-5s %-15s %-20s %-18s %-10s %-10s%n";

            display("=".repeat(83));
            display(String.format(format, "ID", "NAME", "HABITAT", "SPECIES", "DANGER", "CONDITION"));
            display("=".repeat(83));

            for (Creature c : creatures) {
                display(String.format(format,
                        c.getId(),
                        c.getName(),
                        c.getHabitat(),
                        c.getSpecies(),
                        c.getDangerLevel(),
                        c.getCondition()
                ));
            }

            display("=".repeat(83));
            display("Total creatures: " + creatures.size());



    }

    public void viewCreatureByIdMenu(){
        service.viewCreatureByIdSrv();
    }
    public void registerNewCreatureMenu(){
        service.registerNewCreatureSrv();
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
