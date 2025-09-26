package com.example.boardGameCharacterApi.services;

import com.example.boardgamecharacterapi.model.Game;

import java.util.List;
import java.util.Optional;

public interface CharacterTypeService {
    List<CharacterType> getAllCharacterType();
    Optional<CharacterType> getCharacterById(Long id);
    Game saveCharacter(CharacterType characterType);
    void deleteCharacterType(Long id);
}
