package org.example.neonarkclient.client.api;

public interface WardenApiClientInterface {

    void addNewWarden(Warden warden);
    List<Warden> fetchWardens();
    void updateWarden(Warden warden);
    void activateWarden(Long id);
    void deactivateWarden(Long id);

}
