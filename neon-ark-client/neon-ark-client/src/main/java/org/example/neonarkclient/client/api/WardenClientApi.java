package org.example.neonarkclient.client.api;

import org.example.neonarkclient.client.model.Warden;

import java.util.List;

public class WardenClientApi implements WardenApiClientInterface {

    String URL;

    public WardenClientApi(String URL){
        this.URL = URL;
    }
    public Warden addNewWarden(Warden warden){
        return null;
    }
    public List<Warden> fetchWardens(){
        return null;
    }

}
