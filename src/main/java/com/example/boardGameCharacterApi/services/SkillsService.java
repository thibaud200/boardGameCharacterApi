package com.example.boardGameCharacterApi.services;

import com.example.boardgamecharacterapi.model.Game;

import java.util.List;
import java.util.Optional;

public interface SkillsService {
    List<Skills> getAllSkills();
    Optional<Skills> getSkillsById(Long id);
    Skills saveSkill(Skills skill);
    void deleteSkill(Long id);
}
