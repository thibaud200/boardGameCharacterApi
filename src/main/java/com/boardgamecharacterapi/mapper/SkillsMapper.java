package com.boardgamecharacterapi.mapper;

import com.boardgamecharacterapi.models.Characters;
import com.boardgamecharacterapi.models.Skills;
import com.boardgamecharacterapi.models.dto.CharactersDTO;
import com.boardgamecharacterapi.models.dto.SkillsDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SkillsMapper {

    public static SkillsDTO toDTO(Skills skill) {
        if (skill == null) {
            return null;
        }
        return SkillsDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .description(skill.getDescription())
                .build();
    }

    public static List<SkillsDTO> toDTOList(List<Skills> skills) {
        if (skills == null) {
            return Collections.emptyList();
        }
        return skills.stream()
                .map(SkillsMapper::toDTO)
                .collect(Collectors.toList());
    }
}