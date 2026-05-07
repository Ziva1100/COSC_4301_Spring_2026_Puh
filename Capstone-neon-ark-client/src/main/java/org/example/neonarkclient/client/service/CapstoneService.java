package org.example.neonarkclient.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.neonarkclient.client.api.CapstoneApi;
import org.example.neonarkclient.client.exceptions.NotFoundException;
import org.example.neonarkclient.client.model.Creature;

import java.io.IOException;
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

    public void registerNewCreatureSrv(){

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
