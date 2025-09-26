package com.example.boardGameCharacterApi.services;

import com.example.boardgamecharacterapi.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterTypeServiceImpl implements CharacterTypeService {

    @Autowired
    private CharacterTypeRepository characterTypeRepository;

    @Override
    public List<CharacterType> getAllCharactersType() {
        return characterRepository.findAll();
    }

    @Override
    public Optional<CharacterType> getCharacterById(Long id) {
        return characterTypeRepository.findById(id);
    }

    @Override
    public CharacterType saveCharacter(CharacterType characterType) {
        return characterTypeRepository.save(characterType);
    }

    @Override
    public void deleteCharacterType(Long id) {
        characterTypeRepository.deleteById(id);
    }
}
