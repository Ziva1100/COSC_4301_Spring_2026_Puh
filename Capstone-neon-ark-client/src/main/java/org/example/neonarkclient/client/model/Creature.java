package org.example.neonarkclient.client.model;


import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Creature {
    Long id;
    String name;
    String habitat;
    String species;
    String dangerLevel;
    String Condition;
    int deleted;

}
