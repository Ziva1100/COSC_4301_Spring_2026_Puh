package org.example.neonarkclient.client.api;

import org.example.neonarkclient.client.model.Warden;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WardenClientApiMock implements WardenApiClientInterface {

    private final List<Warden> wardens = new ArrayList<>();
    Path path = Path.of("src/main/resources/wardens.csv");

    public Warden addNewWarden(Warden warden){
        wardens.add(warden);
        return warden;
    }
    public List<Warden> fetchWardens(){
        return wardens;
    }


    public String getWardenById(int id) throws IOException{

        String line;
        String[] elements;
        String foundRecord = null;
        BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
        while((line = br.readLine()) != null) {
            elements = line.trim().split(",");
            if ((Integer.parseInt(elements[3]) == id)) {
                foundRecord = line;

            }

        }
        return foundRecord == null ? "" : foundRecord;


    }

}

