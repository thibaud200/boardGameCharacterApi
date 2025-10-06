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
@Table(name = "skills")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // 🔗 Relation inverse vers Characters
    @ManyToMany(mappedBy = "skills")
    private Set<Characters> characters = new HashSet<>();
}
