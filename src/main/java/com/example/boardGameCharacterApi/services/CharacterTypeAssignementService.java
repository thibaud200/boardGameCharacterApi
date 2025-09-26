package com.example.boardGameCharacterApi.services;

import com.example.boardgamecharacterapi.model.Game;

import java.util.List;
import java.util.Optional;

public interface CharacterTypeAssignementService {
    List<Character> getAllCharacterTypeAssignement();
    Optional<CharacterTypeAssignement> getCharacterTypeAssignementById(Long id);
    Game saveCharacterTypeAssignement(Character characterTypeAssignement);
    void deleteCharacterTypeAssignement(Long id);
}
