package org.example.neonarkclient.client.api;


import org.example.neonarkclient.client.model.Warden;

import java.util.List;

public interface WardenApiClientInterface {

    void addNewWarden(Warden warden);
    List<Warden> fetchWardens();
    void updateWarden(Warden warden);
    void activateWarden(Long id);
    void deactivateWarden(Long id);

}
