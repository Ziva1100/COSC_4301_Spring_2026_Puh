package org.example.neonarkclient.client.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.neonarkclient.client.model.Creature;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// This class is an actual API client talking ot the backend springBoot server
public class ApiClient implements CapstoneApi {

    // connection to the local host that connects to backend server
    String url;
    ObjectMapper jsonMapper = new ObjectMapper();

    // set by injection
    public ApiClient(String url) {
        this.url = url;
    }

    // Capstone -- [ List All Creatures ] Menu Choice
    public String listAllCreaturesApi() throws IOException, InterruptedException {

        // recieve the HttpResponse from the controller
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/creatures"))
                .GET()
                .build();

        // Pass the HttpResponse as a string to the server
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Capstone -- [ View Creature By Id ] Menu Choice
    public String getCreatureByIdApi(Long id) throws IOException, InterruptedException {

        // recieve the HttpResponse from the controller
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/creatures/" + id))
                .GET()
                .build();

        // Pass the HttpResponse as a string to the server
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) {

            // handle the 404 not found
            return "404 Creature with ID: " + id + " not found";
        }
        return response.body();
    }

    // Mock API for registering new creature
    // Capstone -- [ Register New Creature ] Menu Choice
    public String registerNewCreatureApi(Creature creature) throws IOException, InterruptedException {

        // turn the creature into a json string
        String json = jsonMapper.writeValueAsString(creature);
        System.out.println("RECEIVED JSON: " + creature);

        // create it into a body to be sent over
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);

        // create a client and build the request to the backend server
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/creatures"))
                .header("Content-Type", "application/json")
                .POST(body)
                .build();

        // receive the response and handle it
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 400)
            return "400 Creature violates database constraint.";

        if (response.statusCode() == 409)
            return "409 Creature already exists.";

        return response.body();
    }

    // Capstone -- [ rename creature] Menu Choice
    public String renameCreatureApi(Long id, String name) throws IOException, InterruptedException {

        String jsonBody = "{\"name\":\"" + name + "\"}";
        // recieve the HttpResponse from the controller
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/creatures/" + id + "/name"))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // Pass the HttpResponse as a string to the server
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();

    }

    // Capstone -- [ View Observations ] Menu Choice
    public String viewCreatureNotesApi(long id) throws IOException, InterruptedException {
        // recieve the HttpResponse from the controller
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/creatures/" + id + "/observations"))
                .GET()
                .build();

        // Pass the HttpResponse as a string to the server
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String creatureFeedingTimeApi(LocalTime time) throws IOException, InterruptedException {

        String timeStr = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        // recieve the HttpResponse from the controller
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/feedings/time?time=" + timeStr))
                .GET()
                .build();

        // Pass the HttpResponse as a string to the server
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Capstone -- [ Remove Creature ] Menu Choice
    public String removeCreatureApi(Long id) throws IOException, InterruptedException {

        String jsonBody = "{\"removed\":\"1\"}";
        // recieve the HttpResponse from the controller
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/creatures/" + id + "/softDelete"))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // Pass the HttpResponse as a string to the server
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) {
            // handle the 404 not found
            return "404";
        }
        if (response.statusCode() == 409) {
            // handle the 409 Conflict with feeding schedule
            return "409";
        }

        return response.body();

    }

    @Override
    public String viewUsersApi(String username, String password) throws IOException, InterruptedException {
        return "";
    }


}


