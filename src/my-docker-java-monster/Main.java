//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #1
//
//  File Name:    Main.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     2/14/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  The runner class for Moster.java method
//
//***************************************************************


public class Main {

    //***************************************************************
    //
    //  Method:       main
    //
    //  Description:  The main method of the program
    //
    //  Parameters:   String array
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public static void main(String args[]){

        Monster monster1 = new Monster("Fireears", "Plasmorphs");

        System.out.println("Your monster has been created.");
        System.out.println(monster1.getDescription());
    }
}