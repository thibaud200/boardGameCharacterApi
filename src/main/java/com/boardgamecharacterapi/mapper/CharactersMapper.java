package com.boardgamecharacterapi.mapper;

import com.boardgamecharacterapi.models.Characters;
import com.boardgamecharacterapi.models.CharactersType;
import com.boardgamecharacterapi.models.dto.CharactersDTO;
import com.boardgamecharacterapi.models.dto.SkillsDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CharactersMapper {

    public CharactersDTO toDTO(Characters character) {
        if (character == null) {
            return null;
        }

        return CharactersDTO.builder()
                .id(character.getId())
                .name(character.getName())
                .description(character.getDescription())
                .gameId(character.getGame() != null ? character.getGame().getId() : null)
                .gameName(character.getGame() != null ? character.getGame().getTitle() : null)
                .skills(character.getSkills() != null ?
                        character.getSkills().stream()
                                .map(this::skillToDTO)
                                .collect(Collectors.toList()) : null)
                .typeNames(character.getTypes() != null ?
                        character.getTypes().stream()
                                .map(CharactersType::getName)
                                .collect(Collectors.toList()) : null)

                .build();
    }

    public Characters toEntity(CharactersDTO dto) {
        if (dto == null) {
            return null;
        }

        Characters character = new Characters();
        character.setId(dto.getId());
        character.setName(dto.getName());
        character.setDescription(dto.getDescription());
        // Le Game et les relations seront gérés dans le service

        return character;
    }

    private SkillsDTO skillToDTO(com.boardgamecharacterapi.models.Skills skill) {
        if (skill == null) {
            return null;
        }

        SkillsDTO dto = new SkillsDTO();
        dto.setId(skill.getId());
        dto.setName(skill.getName());
        dto.setDescription(skill.getDescription());

        return dto;
    }
}