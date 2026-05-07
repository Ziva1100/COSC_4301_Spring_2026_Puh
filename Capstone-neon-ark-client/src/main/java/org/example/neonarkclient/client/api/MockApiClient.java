package org.example.neonarkclient.client.api;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class MockApiClient implements CapstoneApi {

    private final Map<Long, String> mockCreatures = new HashMap<>(Map.of(
            1L, """
            {"id":1,"name":"Nyx","biome":"Dark Forest","species":"Void Fox","dangerLevel":"HIGH","condition":"QUARANTINED","removed":0}
            """,
            2L, """
            {"id":2,"name":"Lumina","biome":"Forest","species":"Glow Moth","dangerLevel":"LOW","condition":"STABLE","removed":0}
            """,
            3L, """
            {"id":3,"name":"Echo","biome":"Cave","species":"Crystal Bat","dangerLevel":"MEDIUM","condition":"STABLE","removed":0}
            """
    ));

    // Mock API that returns a fake JSON list of creatures
    // Capstone -- [ List All Creatures ] Menu Choice
    public String listAllCreaturesApi() {
        return """
            [
              {"id": 1, "name": "Shadowfang", "habitat": "Dark Forest", "species": "Shadow Wolf", "dangerLevel": "HIGH", "condition": "HEALTHY", "removed": 0},
              {"id": 2, "name": "Blaze", "habitat": "Volcanic Ridge", "species": "Fire Drake", "dangerLevel": "CRITICAL", "condition": "INJURED", "removed": 0},
              {"id": 3, "name": "Murk", "habitat": "Wetlands", "species": "Swamp Toad", "dangerLevel": "LOW", "condition": "HEALTHY", "removed": 0},
              {"id": 4, "name": "Frostclaw", "habitat": "Frozen Tundra", "species": "Ice Bear", "dangerLevel": "HIGH", "condition": "STABLE", "removed": 0},
              {"id": 5, "name": "Venom", "habitat": "Jungle Canopy", "species": "Poison Serpent", "dangerLevel": "CRITICAL", "condition": "HEALTHY", "removed": 0}
            ]
            """;
    }

    // Capstone -- [ View Creature By Id ] Menu Choice
    // Mock API that returns either a 404 not found or a record of creature
    public String getCreatureByIdApi(Long id) throws IOException, InterruptedException{

        if (mockCreatures.containsKey(id)) {
            return """
            {"id":1,"name":"Nyx","biome":"Dark Forest","species":"Void Fox","dangerLevel":"HIGH","condition":"QUARANTINED","removed":0}
            """;
        } else {
            return "404 Creature with ID: " + id + " not found";
        }
    }

}
