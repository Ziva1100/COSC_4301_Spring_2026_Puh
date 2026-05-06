package org.example.neonarkclient.client.api;


import org.example.neonarkclient.client.model.Warden;

import java.io.IOException;
import java.util.List;

public interface WardenApiClientInterface {

    String addNewWarden(Warden warden) throws IOException;
    List<Warden> fetchWardens();
    String getWardenById(int id) throws IOException;

}
