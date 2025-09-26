package com.example.boardgamecharacterapi.model;

import jakarta.persistence.*;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Description;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "charactersType")       
public class CharacterType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Name
    private String name;
    @Description
    private String description;

    @ManyToMany(mappedBy = "types")
    private Set<Character> characters;
}


