package com.example.boardGameCharacterApi.services;

import com.example.boardgamecharacterapi.model.Game;
import com.example.boardgamecharacterapi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillsServiceImpl implements SkillsService {

    @Autowired
    private SkillsRepository skillRepository;

    @Override
    public List<Skills> getAllGames() {
        return skillRepository.findAll();
    }

    @Override
    public Optional<Skills> getSkillsById(Long id) {
        return skillRepository.findById(id);
    }

    @Override
    public Skills saveSkills(Skills skills) {
        return gameRepository.save(skills);
    }

    @Override
    public void deleteSkills(Long id) {
        skillsRepository.deleteById(id);
    }
}
