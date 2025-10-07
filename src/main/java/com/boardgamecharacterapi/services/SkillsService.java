package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.dto.SkillsDTO;
import java.util.List;
import java.util.Optional;

public interface SkillsService {
    List<SkillsDTO> getAllSkills();
    Optional<SkillsDTO> getSkillsById(Long id);
    Optional<SkillsDTO> searchSkillByName(String name);
    SkillsDTO saveSkill(SkillsDTO skill);
    SkillsDTO updateSkill(SkillsDTO skill);
    void deleteSkill(Long id);
}
