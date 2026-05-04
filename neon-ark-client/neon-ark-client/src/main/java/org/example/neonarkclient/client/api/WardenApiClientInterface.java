package org.example.neonarkclient.client.api;


import org.example.neonarkclient.client.model.Warden;

import java.util.List;

public interface WardenApiClientInterface {

    Warden addNewWarden(Warden warden);
    List<Warden> fetchWardens();

}
