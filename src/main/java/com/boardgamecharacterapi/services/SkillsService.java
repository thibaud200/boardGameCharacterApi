package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.Skills;
import java.util.List;
import java.util.Optional;

public interface SkillsService {
    List<Skills> getAllSkills();
    Optional<Skills> getSkillsById(Long id);
    Skills saveSkill(Skills skill);
    Skills updateSkill(Long id, Skills skill);
    void deleteSkill(Long id);
}
