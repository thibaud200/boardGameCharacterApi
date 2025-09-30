package com.boardgamecharacterapi.mapper;
import com.boardgamecharacterapi.models.Characters;
import com.boardgamecharacterapi.models.dto.CharactersDTO;
import com.boardgamecharacterapi.models.Games;
import com.boardgamecharacterapi.models.dto.GamesDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GamesMapper {

    public GamesDTO toDTO(Games game) {
        if (game == null) {
            return null;
        }

        return GamesDTO.builder()
                .id(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .title(game.getTitle() != null ? game.getTitle() : null)
                .characters(game.getCharacters() != null ?
                        game.getCharacters().stream()
                                .map(Games::getCharacters)
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
        // Le Game et les relations seront gérés dans le service

        return game;
    }

    private CharactersDTO characterToDTO(com.boardgamecharacterapi.models.Characters characters) {
        if (characters == null) {
            return null;
        }

        CharactersDTO dto = new CharactersDTO();
        dto.setId(characters.getId());
        dto.setName(characters.getName());
        dto.setDescription(characters.getDescription());

        return dto;
    }
}