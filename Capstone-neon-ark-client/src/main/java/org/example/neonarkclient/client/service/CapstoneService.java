package org.example.neonarkclient.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.neonarkclient.client.api.CapstoneApi;
import org.example.neonarkclient.client.exceptions.NotFoundException;
import org.example.neonarkclient.client.model.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;


// the service for the client handling logic of the menu choice
public class CapstoneService {
    CapstoneApi clientApi;
    ObjectMapper jsonMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    public CapstoneService(CapstoneApi clientApi) {
        this.clientApi = clientApi;
    }

    // the front-end service for returning a list of all creatures
    // Capstone -- [ List All Creatures ] Menu Choice
    public List<Creature> listAllCreaturesSrv() throws IOException, InterruptedException {

        // parse the JSON received by the client from the back end into a creature list to be displayed
        String creatureJson = clientApi.listAllCreaturesApi();
        return jsonMapper.readValue(creatureJson, new TypeReference<List<Creature>>(){});
    }

    // Capstone -- [ View Creature By Id ] Menu Choice
    // get the creature by ID, handle the 404 not found with a custom exception
    public Creature viewCreatureByIdSrv(int id) throws IOException, InterruptedException {
        Long idL = (long)id;
        String response = clientApi.getCreatureByIdApi(idL);

        // return a simple 404 not found if nothing was returned
        if(response.startsWith("404")){
            throw new NotFoundException(idL);
        }

        // if the id created a match, pass the info as a creature
        return jsonMapper.readValue(response, Creature.class);
    }

    // Capstone -- [ Register New Creature ] Menu Choice
    public Creature registerNewCreatureSrv(Creature creature) throws IOException, InterruptedException {
        String response = clientApi.registerNewCreatureApi(creature);
        if (response.startsWith("400")){
            throw new IllegalArgumentException("A database constraint was violated.");
        } else if (response.startsWith("409")) {
            throw new IllegalArgumentException("This creature already exists.");
        }
        return jsonMapper.readValue(response, Creature.class);
    }

    // Add helper functions that will validate the input from the menu for
    // registering new creature
    // validate the name of the creature
    public String validateCreatureName(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("First name cannot be blank.");
        return input.trim();
    }

    // validate habitat / biome
    public String validateBiome(String input){
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("Habitat cannot be blank.");

        try {
           Biome biome = Biome.valueOf((input.toUpperCase().trim()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid habitat. Options: "
                    + Arrays.toString(Biome.values()));
        }

        return input.toUpperCase().trim();
    }

    // validate species
    public String validateSpecies(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("First name cannot be blank.");
        return input.trim();
    }

    // validate dangerLevel
    public String validateDangerLevel(String input){
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("Habitat cannot be blank.");

        try {
            DangerLevel danger = DangerLevel.valueOf((input.toUpperCase().trim()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid danger level. Options: "
                    + Arrays.toString(DangerLevel.values()));
        }

        return input.toUpperCase().trim();
    }

    // validate condition
    public String validateCondition(String input){
        if (input == null || input.isBlank())
            throw new IllegalArgumentException("Habitat cannot be blank.");

        try {
            Condition con = Condition.valueOf((input.toUpperCase().trim()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid condition. Options: "
                    + Arrays.toString(Condition.values()));
        }

        return input.toUpperCase().trim();
    }

    // Capstone -- [ rename creature] Menu Choice
    public Creature renameCreatureSrv(int id, String newName) throws IOException, InterruptedException {
        String response = clientApi.renameCreatureApi((long)id, newName);

        return jsonMapper.readValue(response, Creature.class);
    }


    // Capstone -- [ View Observations ] Menu Choice
    public List<Observation> viewCreatureNotesSrv(int id) throws IOException, InterruptedException {
        String creatureJson = clientApi.viewCreatureNotesApi((long) id);
        return jsonMapper.readValue(creatureJson, new TypeReference<List<Observation>>(){});
    }

    // Capstone -- [ Find Creatures By Feeding Time ] Menu Choice
    public List<Feeding> creatureFeedingTimeSrv(LocalTime time) throws IOException, InterruptedException {
        String feedingJson = clientApi.creatureFeedingTimeApi(time);


        if (feedingJson.startsWith("400")) {
            throw new IllegalArgumentException("There was an issue with the time.");
        }
        return jsonMapper.readValue(feedingJson, new TypeReference<List<Feeding>>() {
        });

    }

    // validate the hour
    // Capstone -- [ Find Creatures By Feeding Time ] Menu Choice
    public int validateHour(int h) {
        if (h < 0 || h > 23) {
            throw new IllegalArgumentException("The hour has to be 0-23.");
        }
        return h;
    }

    // validate the minutes
    // Capstone -- [ Find Creatures By Feeding Time ] Menu Choice
    public int validateMinute(int m) {
        if (m < 0 || m >= 60) {
            throw new IllegalArgumentException("The minutes have to be 0-59.");
        }
        return m;

    }

    public void viewUsersSrv(){

    }




}
