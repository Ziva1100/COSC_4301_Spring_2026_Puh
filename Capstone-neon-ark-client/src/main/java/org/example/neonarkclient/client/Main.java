package org.example.neonarkclient.client;

import org.example.neonarkclient.client.api.ApiClient;
import org.example.neonarkclient.client.api.CapstoneApi;
import org.example.neonarkclient.client.api.MockApiClient;
import org.example.neonarkclient.client.menu.CapstoneMenu;
import org.example.neonarkclient.client.service.CapstoneService;


public class Main {
    public static void main(String[] args) {

        boolean useMock = false;

        CapstoneApi api = useMock
                ? new MockApiClient()
                : new ApiClient("http://localhost:8080");

        CapstoneService service = new CapstoneService(api);
        CapstoneMenu menu = new CapstoneMenu(service);
        menu.run();


    }
}