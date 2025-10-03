package com.boardgamecharacterapi.controller;

import com.boardgamecharacterapi.services.*;
import com.boardgamecharacterapi.models.dto.CharactersDTO;
import com.boardgamecharacterapi.models.dto.GamesDTO;
import com.boardgamecharacterapi.mapper.CharactersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final GamesService gamesService;
    private final CharactersService charactersService;
    private final SkillsService skillsService;
    private final CharactersTypeService charactersTypeService;
    private final CharactersMapper charactersMapper;

    @Autowired
    public SearchController(
            GamesService gamesService,
            CharactersService charactersService,
            SkillsService skillsService,
            CharactersTypeService charactersTypeService,
            CharactersMapper charactersMapper
    ) {
        this.gamesService = gamesService;
        this.charactersService = charactersService;
        this.skillsService = skillsService;
        this.charactersTypeService = charactersTypeService;
        this.charactersMapper = charactersMapper;
    }

    @GetMapping("/games/{gameId}/characters")
    public ResponseEntity<List<CharactersDTO>> searchCharacters(@PathVariable Long gameId) {
        List<CharactersDTO> characters = charactersService.getCharactersByGameId(gameId);
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/characters")
    public ResponseEntity<List<CharactersDTO>> getAllCharacters() {
        List<CharactersDTO> characters = charactersService.getAllCharacters();
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/games")
    public ResponseEntity<List<GamesDTO>> searchGames() {
        List<GamesDTO> games = gamesService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<GamesDTO> getGameById(@PathVariable Long gameId) {
        Optional<GamesDTO> gameOpt = gamesService.getGameById(gameId);
        return gameOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
