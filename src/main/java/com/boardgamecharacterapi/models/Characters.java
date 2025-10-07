package com.boardgamecharacterapi.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "characters")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Characters {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // 🔗 Relation avec Games
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Games game;

    // 🔗 Relation avec Type
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private Type type;

    // 🔗 Relation avec Skills (ManyToMany)
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "characters_skills",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skills> skills = new HashSet<>();

    // 🔁 Utilise equals() et hashCode() basés sur id pour éviter les doublons dans le Set
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Characters that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
