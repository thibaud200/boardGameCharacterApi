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
@Table(name = "types")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // 🔗 Un type peut être utilisé par plusieurs personnages
    @OneToMany(mappedBy = "type")
    private Set<Characters> characters = new HashSet<>();
}
