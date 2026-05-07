package org.example.neonarkclient.client.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Creature {
    Long id;
    String name;
    String biome;
    String species;
    String dangerLevel;
    String Condition;
    int removed;

}
