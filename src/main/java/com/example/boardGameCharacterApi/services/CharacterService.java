package com.example.boardGameCharacterApi.services;

import com.example.boardgamecharacterapi.model.Game;

import java.util.List;
import java.util.Optional;

public interface CharacterService {
    List<Character> getAllCharacter();
    Optional<Character> getCharacterById(Long id);
    Game saveCharacter(Game character);
    void deleteCharacter(Long id);
}
