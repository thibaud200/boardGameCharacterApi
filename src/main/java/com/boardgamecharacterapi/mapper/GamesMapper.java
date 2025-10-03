package com.boardgamecharacterapi.mapper;

import com.boardgamecharacterapi.models.dto.CharactersDTO;
import com.boardgamecharacterapi.models.Games;
import com.boardgamecharacterapi.models.dto.GamesDTO;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class GamesMapper {

    private final CharactersMapper charactersMapper;

    public GamesMapper(CharactersMapper charactersMapper) {
        this.charactersMapper = charactersMapper;
    }

    public GamesDTO toDTO(Games game) {
        if (game == null) {
            return null;
        }

        return GamesDTO.builder()
                .id(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .characters(game.getCharacters() != null ?
                        game.getCharacters().stream()
                                .map(charactersMapper::toDTO)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public Games toEntity(GamesDTO dto) {
        if (dto == null) {
            return null;
        }

        Games game = new Games();
        game.setId(dto.getId());
        game.setTitle(dto.getTitle());
        game.setDescription(dto.getDescription());
        // Les relations (personnages) sont gérées dans le service

        return game;
    }
}
