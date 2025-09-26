package com.example.boardgamecharacterapi.model.dto;

import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class CharacterDTO {
    private Long id;
    private String name;
    private String description;
    private Long gameId;
    private List<SkillDTO> skills;
    private Set<Long> typeIds;
}
