package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.dto.CharactersDTO;

import java.util.List;
import java.util.Optional;

public interface CharactersService {
    List<CharactersDTO> getAllCharacters();
    Optional<CharactersDTO> getCharacterById(Long id);
    List<CharactersDTO> getCharactersByGameId(Long gameId);
    CharactersDTO saveCharacter(CharactersDTO character);
    CharactersDTO updateCharacter(CharactersDTO character);
    void deleteCharacter(Long id);
}