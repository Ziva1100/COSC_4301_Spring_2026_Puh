package org.example.neonarkclient.client.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Creature {
    Long id;
    String name;
    String habitat;
    String species;
    String dangerLevel;
    String Condition;
    int removed;

}
