package org.example.neonarkclient.client.api;

import org.example.neonarkclient.client.model.Creature;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// This class is an actual API client talking ot the backend springBoot server
public class ApiClient implements CapstoneApi {

    // connection to the local host that connects to backend server
    String url;

    // set by injection
    public ApiClient(String url) {
        this.url = url;
    }

    // Capstone -- [ List All Creatures ] Menu Choice
    public String listAllCreaturesApi() throws IOException, InterruptedException {

        // recieve the HttpResponse from the controller
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+"/api/creatures"))
                .GET()
                .build();

        // Pass the HttpResponse as a string to the server
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Capstone -- [ View Creature By Id ] Menu Choice
    public String getCreatureByIdApi(Long id) throws IOException, InterruptedException{

        // recieve the HttpResponse from the controller
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+"/api/creatures/"+id))
                .GET()
                .build();

        // Pass the HttpResponse as a string to the server
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404){

            // handle the 404 not found
            return "404 Creature with ID: "+id+" not found";
        }
        return response.body();
    }

    // Mock API for registering new creature
    // Capstone -- [ Register New Creature ] Menu Choice
    public String registerNewCreatureApi(Creature creature) throws IOException, InterruptedException{
        return null;
    }

}



