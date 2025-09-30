package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.Characters;
import java.util.List;
import java.util.Optional;

public interface CharactersService {
    List<Characters> getAllCharacters();
    Optional<Characters> getCharacterById(Long id);
    List<Characters> getCharactersByGameId(Long gameId);
    Characters saveCharacter(Characters character);
    Characters updateCharacter(Long id, Characters character);
    void deleteCharacter(Long id);
}