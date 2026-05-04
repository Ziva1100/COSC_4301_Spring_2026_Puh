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

import java.util.InputMismatchException;
import java.util.Scanner;

public class WardenMenu {

    private Scanner scan;
    private final WardenService service;
    private int userInput;

    public WardenMenu(WardenService service){
        this.service = service;
    }

    public void run(){
        userInput = 0;

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
            }catch (InputMismatchException e) {
                display("The answer has to be a number!");
                scan.nextInt();
            }

            switch (userInput) {
                case 1 -> service.addNewWarden();
                case 2 -> viewWardensMenu();
                case 3 -> updateWardenMenu();
                case 4 -> manageCertificatesMenu();
                case 5 -> deactWardenMenu();
            }

            service.handleMenuChoice(userInput);
        }

    }

    private void display(String str){
        System.out.println(str);
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
