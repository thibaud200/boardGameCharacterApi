package com.example.boardGameCharacterApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterTypeAssignementServiceImpl implements CharacterTypeAssignementService {

    @Autowired
    private CharacterTypeAssignementRepository characterTypeAssignementRepository;

    @Override
    public List<CharacterTypeAssignement> getAllCharactersTypeAssignement() {
        return characterTypeAssignementRepository.findAll();
    }

    @Override
    public Optional<CharacterTypeAssignement> getCharacterTypeAssignementById(Long id) {
        return characterTypeAssignementRepository.findById(id);
    }

    @Override
    public CharacterTypeAssignement saveCharacterTypeAssignement(CharacterTypeAssignement characterTypeAssignement) {
        return characterTypeAssignementRepository.save(characterTypeAssignement);
    }

    @Override
    public void deleteCharacterTypeAssignement(Long id) {
        characterTypeAssignementRepository.deleteById(id);
    }
}
