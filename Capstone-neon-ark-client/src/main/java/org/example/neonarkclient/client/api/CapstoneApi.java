package org.example.neonarkclient.client.api;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface CapstoneApi {
    String listAllCreaturesApi() throws IOException, InterruptedException;
    String getCreatureByIdApi(Long id) throws IOException, InterruptedException;
}
