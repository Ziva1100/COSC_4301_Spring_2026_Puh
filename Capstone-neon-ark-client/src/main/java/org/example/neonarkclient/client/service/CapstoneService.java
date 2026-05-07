package org.example.neonarkclient.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.neonarkclient.client.api.CapstoneApi;
import org.example.neonarkclient.client.model.Creature;

import java.io.IOException;
import java.util.List;

public class CapstoneService {
    CapstoneApi clientApi;
    ObjectMapper jsonMapper = new ObjectMapper();


    public CapstoneService(CapstoneApi clientApi) {
        this.clientApi = clientApi;
    }

    public List<Creature> listAllCreaturesSrv() throws IOException, InterruptedException {
        String creatureJson = clientApi.listAllCreaturesApi();
        return jsonMapper.readValue(creatureJson, new TypeReference<List<Creature>>(){});
    }


    public void viewCreatureByIdSrv(){

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
