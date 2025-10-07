package com.boardgamecharacterapi.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "skills")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Skills {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // 🔗 Relation inverse vers Characters
    @Builder.Default
    @ManyToMany(mappedBy = "skills")
    private Set<Characters> characters = new HashSet<>();

    // 🔁 equals / hashCode basés sur id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skills that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
