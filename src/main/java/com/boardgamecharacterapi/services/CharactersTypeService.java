package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.CharactersType;
import java.util.List;
import java.util.Optional;

public interface CharactersTypeService {
    List<CharactersType> getAllCharactersType();
    Optional<CharactersType> getCharacterTypeById(Long id);
    CharactersType saveCharacterType(CharactersType characterType);
    CharactersType updateCharacterType(Long id, CharactersType characterType);
    void deleteCharacterType(Long id);
}
