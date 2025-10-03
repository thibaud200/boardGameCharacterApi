package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.dto.TypeDTO;

import java.util.List;
import java.util.Optional;

public interface CharactersTypeService {
    List<TypeDTO> getAllCharactersType();
    Optional<TypeDTO> getCharacterTypeById(Long id);
    TypeDTO saveCharacterType(TypeDTO characterType);
    TypeDTO updateCharacterType(Long id, TypeDTO characterType);
    void deleteCharacterType(Long id);
}
