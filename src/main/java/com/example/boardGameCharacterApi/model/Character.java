package com.example.boardgamecharacterapi.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Description;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Name
    private String name;

    @Description()
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
    private List<Skill> skills;

    @ManyToMany
    @JoinTable(
            name = "character_type_assignment",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
}
