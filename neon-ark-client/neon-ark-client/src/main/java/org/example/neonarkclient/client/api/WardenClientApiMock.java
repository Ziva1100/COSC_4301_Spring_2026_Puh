package org.example.neonarkclient.client.api;

import org.example.neonarkclient.client.model.Clearance;
import org.example.neonarkclient.client.model.IdType;
import org.example.neonarkclient.client.model.Status;
import org.example.neonarkclient.client.model.Warden;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WardenClientApiMock implements WardenApiClientInterface {

    private final List<Warden> wardens = new ArrayList<>();
    Path path = Path.of("src/main/resources/wardens.csv");

    public Warden addNewWarden(Warden warden){
        wardens.add(warden);
        return warden;
    }
    public List<Warden> fetchWardens() throws IOException{

        String line;
        String[] elements;
        String[] header;
        Warden warden = null;
        try (BufferedReader br = new BufferedReader((new FileReader(path.toFile())))) {

            int firstLineCounter = 0;
            while ((line = br.readLine()) != null) {
                if (firstLineCounter == 0) {
                    header = line.trim().split(",");
                } else {
                    elements = line.trim().split(",");
                    int id = Integer.parseInt(elements[3]);
                    LocalDate startDate = LocalDate.parse(elements[9]);
                    LocalDate endDate = null;
                    if (!elements[10].isEmpty())
                        endDate = LocalDate.parse(elements[10]);
                    warden = Warden.builder()
                            .firstName(elements[1])
                            .lastName(elements[2])
                            .id(id)
                            .idType(IdType.valueOf(elements[4].toUpperCase()))
                            .email(elements[5])
                            .role(elements[6])
                            .status(Status.valueOf(elements[7].toUpperCase()))
                            .clearance(Clearance.valueOf(elements[8].toUpperCase()))
                            .startDate(startDate)
                            .endDate(endDate)
                            .dimension(elements[11])
                            .build();
                    wardens.add(warden);
                }
                firstLineCounter++;
            }
        }
        return wardens;
    }


    public String getWardenById(int id) throws IOException{

        String line;
        String[] elements;
        String foundRecord = null;
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            int firstLineCounter = 0;
            while ((line = br.readLine()) != null) {
                if (firstLineCounter != 0) {
                    elements = line.trim().split(",");
                    if ((Integer.parseInt(elements[3]) == id)) {
                        foundRecord = line;

                    }
                }
                firstLineCounter++;

            }
        }
        return foundRecord == null ? "" : foundRecord;


    }

}

