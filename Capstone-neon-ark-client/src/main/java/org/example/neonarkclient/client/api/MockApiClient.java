package org.example.neonarkclient.client.api;

import org.example.neonarkclient.client.model.Creature;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

// this is a mock api client ment to imitade the actual api call to
// prepare front end for connection to backend
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

    // Mock API for registering new creature
    // Capstone -- [ Register New Creature ] Menu Choice
    public String registerNewCreatureApi(Creature creature) throws IOException, InterruptedException {

        // simulate 400 bad request — validation failure
        if (creature.getName() == null || creature.getName().isBlank())
            return "400 Name cannot be blank";
        if (creature.getSpecies() == null || creature.getSpecies().isBlank())
            return "400 Species cannot be blank";
        if (creature.getBiome() == null || creature.getBiome().isBlank())
            return "400 Biome cannot be blank";
        if (creature.getDangerLevel() == null || creature.getDangerLevel().isBlank())
            return "400 Danger level cannot be blank";
        if (creature.getCondition() == null || creature.getCondition().isBlank())
            return "400 Condition cannot be blank";

        // simulate 409 conflict — duplicate name
        boolean nameExists = mockCreatures.values().stream()
                .anyMatch(json -> json.contains("\"name\":\"" + creature.getName() + "\""));
        if (nameExists)
            return "409 A creature with the name '" + creature.getName() + "' already exists";

        // simulate 201 created — generate a new id and add to mock map
        Long newId = mockCreatures.keySet().stream().max(Long::compareTo).orElse(0L) + 1;

        String newCreatureJson = String.format(
                """
                {"id":%d,"name":"%s","biome":"%s","species":"%s","dangerLevel":"%s","condition":"%s","removed":0}
                """,
                newId,
                creature.getName(),
                creature.getBiome(),
                creature.getSpecies(),
                creature.getDangerLevel(),
                creature.getCondition()
        );

        mockCreatures.put(newId, newCreatureJson);
        return newCreatureJson;
    }

    // Capstone -- [ rename creature] Menu Choice
    // Mock API for renaming the creature
    // no error returns because all the calidation is done in viewById
    public String renameCreatureApi(Long id, String name) throws IOException, InterruptedException{
        // pull the existing JSON string and replace the name value
        String existing = mockCreatures.get(id);
        String updated = existing.replaceAll("\"name\":\"[^\"]*\"", "\"name\":\"" + name + "\"");

        // save updated string back to mock map
        mockCreatures.put(id, updated);

        return updated;
    }

}
