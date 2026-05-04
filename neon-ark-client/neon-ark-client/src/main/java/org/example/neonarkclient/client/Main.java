//***************************************************************
//
//  Developer:    Ziva Puh
//
//  Project #:    Project #4
//
//  File Name:    Main.java
//
//  Course:       COSC 4301 Modern Programming
//
//  Due Date:     5/14/2026
//
//  Instructor:   Prof. Jon-Mikel Pearson
//
//  Description:  the runner class for the client. it startsup the menu
//
//***************************************************************

package org.example.neonarkclient.client;


public class Main {

    //***************************************************************
    //
    //  Method:       main()
    //
    //  Description:  runs the client. If the mock flag is set to true
    //  the main is run as a mock and does not connect ot the backend server
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public static void main(String[] args) {

        boolean useMock = true;

        WardenApi api = useMock
                ? new MockWardenApiClient()
                : new WardenApiClient("http://localhost:8080");

        WardenService service = new WardenService(api);
        WardenMenu menu = new WardenMenu(service);
        menu.run();


    }
}