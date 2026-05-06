package org.example.neonarkclient.client.api;

import org.example.neonarkclient.client.model.Warden;

import java.io.IOException;
import java.util.List;

public class WardenClientApi implements WardenApiClientInterface {

    String URL;

    public WardenClientApi(String URL){
        this.URL = URL;
    }
    public String addNewWarden(Warden warden){
        return null;
    }
    public List<Warden> fetchWardens(){
        return null;
    }


    public String getWardenById(int id) throws IOException {
        return null;
    }
}
