package org.example.neonarkclient.client.api;

import org.example.neonarkclient.client.model.Creature;

import java.io.IOException;
import java.net.http.HttpResponse;

// this is an interface to be implemented by mock and by actual api client
public interface CapstoneApi {
    String listAllCreaturesApi() throws IOException, InterruptedException;
    String getCreatureByIdApi(Long id) throws IOException, InterruptedException;
    String registerNewCreatureApi(Creature creature) throws IOException, InterruptedException;
    String renameCreatureApi(Long id, String name) throws IOException, InterruptedException;
}
