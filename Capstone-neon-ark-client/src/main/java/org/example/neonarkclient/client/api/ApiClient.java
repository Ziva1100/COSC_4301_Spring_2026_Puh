package org.example.neonarkclient.client.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient implements CapstoneApi {

    String url;

    public ApiClient(String url) {
        this.url = url;
    }

    // Capstone -- [ List All Creatures ] Menu Choice
    public String listAllCreaturesApi() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+"/api/creatures"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Capstone -- [ View Creature By Id ] Menu Choice
    public String getCreatureByIdApi(Long id) throws IOException, InterruptedException{

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+"/api/creatures/"+id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404){
            return "404 Creature with ID: "+id+" not found";
        }
        return response.body();
    }

}

