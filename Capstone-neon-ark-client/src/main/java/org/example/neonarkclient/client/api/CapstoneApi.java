package org.example.neonarkclient.client.api;

import java.io.IOException;
import java.net.http.HttpResponse;

// this is an interface to be implemented by mock and by actual api client
public interface CapstoneApi {
    String listAllCreaturesApi() throws IOException, InterruptedException;
    String getCreatureByIdApi(Long id) throws IOException, InterruptedException;
}
