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
@Table(name = "character_types")       
public class CharacterType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "types")
    private Set<Character> characters;
}



