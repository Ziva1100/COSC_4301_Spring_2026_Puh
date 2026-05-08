package org.example.neonarkclient.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.neonarkclient.client.api.CapstoneApi;
import org.example.neonarkclient.client.exceptions.NotFoundException;
import org.example.neonarkclient.client.model.Biome;
import org.example.neonarkclient.client.model.Condition;
import org.example.neonarkclient.client.model.Creature;
import org.example.neonarkclient.client.model.DangerLevel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


// the service for the client handling logic of the menu choice
public class CapstoneService {
    CapstoneApi clientApi;
    ObjectMapper jsonMapper = new ObjectMapper();


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

    public void renameCreatureSrv(){

    }

    public void viewCreatureNotesSrv(){

    }

    public void creatureFeedingTimeSrv(){

    }

    public void viewUsersSrv(){

    }




}
