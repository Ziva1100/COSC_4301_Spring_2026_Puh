package org.example.neonarkclient.client.menu;

import org.example.neonarkclient.client.service.CapstoneService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CapstoneMenu
{
    private final Scanner scan = new Scanner(System.in);
    private final CapstoneService service;

    public CapstoneMenu(CapstoneService service){
        this.service = service;
    }

    public run(){
        int userInput = 1;
        while(userInput != 0) {

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
                case 1 -> listAllCreatures();

                default -> display("The menu choice was incorrect. " +
                        "Please, try again!");
            }



        }
    }
    public void listAllCreatures(){
        service.viewAllCreatures();
    }

    private void display(String str){
        System.out.println(str);
    }

    private String promptString(String str){
        System.out.println(str);
        return scan.nextLine();

    }

}
