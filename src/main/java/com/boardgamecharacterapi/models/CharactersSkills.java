package com.boardgamecharacterapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "characters_skills")
public class CharactersSkills {

    @ManyToMany
    @JoinTable(
            name = "characters",
            joinColumns = @JoinColumn(name = "characters_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Long idCharacter;
    @ManyToMany
    @JoinTable(
            name = "skills",
            joinColumns = @JoinColumn(name = "idCharacter"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Long idSkill;

}



