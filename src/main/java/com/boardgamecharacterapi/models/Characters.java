package com.boardgamecharacterapi.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "characters")
public class Characters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Games game;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToMany
    @JoinTable(
            name = "characters_skills",
            joinColumns = @JoinColumn(name = "idCharacter"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skills> skills = new HashSet<>();
}

