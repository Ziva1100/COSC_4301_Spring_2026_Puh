//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #1
//
//  File Name:    Monster.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     2/14/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  This class will create a simple monster
//
//***************************************************************

public class Monster {
    private String name;
    private String type;

    /******** CONSTRUCTORS ********/
    public Monster(String name, String type){
        this.name = name;
        this.type = type;
    }

    /******** GETTERS **********/
    public String getDescription(){
        return name+" is a "+type+"-type monster from the Neon Ark training program.";
    }



}