package org.example.neonarkclient.client.api;

import org.example.neonarkclient.client.model.Creature;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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
    // Capstone -- [ View Observations ] Menu Choice
    // get fake observations in the json shape as the real ones
    // 404 Not Found handeled by viewById
    public String viewCreatureNotesApi(long id) throws IOException, InterruptedException{
        String creatureName = mockCreatures.get(id)
                .replaceAll(".*\"name\":\"([^\"]+)\".*", "$1").trim();

        return String.format("""
            [
              {"id":1,"creature":"%s","date":"2026-01-05","category":"MEDICAL","observation":"Showing signs of light sensitivity. Recommend blackout curtains."},
              {"id":2,"creature":"%s","date":"2026-01-12","category":"BEHAVIOR","observation":"Pacing the perimeter repeatedly during night cycle."},
              {"id":3,"creature":"%s","date":"2026-02-01","category":"SAFETY","observation":"Attempted to escape through ventilation shaft. Sealed and reinforced."},
              {"id":4,"creature":"%s","date":"2026-02-14","category":"ACCOUNTABILITY","observation":"Feeding log updated. Consuming standard rations without issue."}
            ]
            """, creatureName, creatureName, creatureName, creatureName);
    }

    // Capstone -- [ Find Creatures By Feeding Time ] Menu Choice
// Mock API that returns a list of feedings at a given time
// 400 Bad Request if time format is invalid
    public String creatureFeedingTimeApi(LocalTime time) throws IOException, InterruptedException {



        return String.format("""
            [
              {"name":"Nyx","food":"Raw phosphorescent fish","quantity":"200g","time":"%s"},
              {"name":"Lumina","food":"Phosphorescent moss","quantity":"50g","time":"%s"},
              {"name":"Echo","food":"Crystal insects","quantity":"100g","time":"%s"}
            ]
            """, time, time, time);
    }

    // Capstone -- [ Remove Creature ] Menu Choice
    public String removeCreatureApi(Long id) throws IOException, InterruptedException {
        // 404 — creature not found
        if (!mockCreatures.containsKey(id)) {
            return "404 Creature with ID: " + id + " not found";
        }

        // 409 — simulate active feeding conflict for creature ID 1
        if (id == 1L) {
            return "409 Cannot remove creature with active feeding schedule";
        }

        // soft remove — update the removed field to 1
        String existing = mockCreatures.get(id);
        String updated = existing.replaceAll("\"removed\":0", "\"removed\":1");
        mockCreatures.put(id, updated);

        return updated;
    }

    // Capstone -- [ View All System Users ] Menu Choice
    // Mock API that returns a list of users
    // 401 Unauthorized if credentials are incorrect
    public String viewUsersApi(String username, String password) throws IOException, InterruptedException {
        // simulate 401 — invalid credentials
        if (username == null || password == null) {
            return "401 Unauthorized — invalid credentials";
        }

        // valid users map — username -> {password, role}
        Map<String, String[]> users = Map.of(
                "admin",    new String[]{"admin123",   "ADMIN"},
                "warden",   new String[]{"neonark123", "USER"},
                "keeper",   new String[]{"keeper123",  "USER"}
        );

        // check if user exists and password matches
        if (!users.containsKey(username) || !users.get(username)[0].equals(password)) {
            return "401 Unauthorized — invalid credentials";
        }

        // check if user has admin role
        if (!users.get(username)[1].equals("ADMIN")) {
            return "403 Forbidden — admin access required";
        }

        // 200 — return user list
        return """
    [
      {
        "id": 1,
        "firstName": "Elena",
        "lastName": "Voss",
        "idType": "BADGE",
        "email": "elena.voss@neonark.com",
        "role": "ADMIN",
        "status": "ACTIVE",
        "clearance": "ECLIPSE",
        "startDate": "2022-03-15",
        "endDate": null,
        "dimension": "PRIME"
      },
      {
        "id": 2,
        "firstName": "Marcus",
        "lastName": "Hale",
        "idType": "BADGE",
        "email": "marcus.hale@neonark.com",
        "role": "WARDEN",
        "status": "ACTIVE",
        "clearance": "ALPHA",
        "startDate": "2023-01-10",
        "endDate": null,
        "dimension": "PRIME"
      },
      {
        "id": 3,
        "firstName": "Sable",
        "lastName": "Quinn",
        "idType": "VISA",
        "email": "sable.quinn@neonark.com",
        "role": "KEEPER",
        "status": "ONLEAVE",
        "clearance": "OMEGA",
        "startDate": "2023-06-01",
        "endDate": "2026-06-01",
        "dimension": "SHADOW"
      },
      {
        "id": 4,
        "firstName": "Orion",
        "lastName": "Blake",
        "idType": "PASSPORT",
        "email": "orion.blake@neonark.com",
        "role": "SUPERVISOR",
        "status": "TERMINATED",
        "clearance": "ALPHA",
        "startDate": "2021-09-20",
        "endDate": "2025-09-20",
        "dimension": "VOID"
      }
    ]
    """;
    }


}
