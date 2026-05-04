package org.example.neonarkclient.client.api;

import org.example.neonarkclient.client.model.Warden;

import java.util.ArrayList;
import java.util.List;

public class WardenClientApiMock implements WardenApiClientInterface {

    private final List<Warden> wardens = new ArrayList<>();

    public Warden addNewWarden(Warden warden){
        wardens.add(warden);
        return warden;
    }
    public List<Warden> fetchWardens(){

    }

}
