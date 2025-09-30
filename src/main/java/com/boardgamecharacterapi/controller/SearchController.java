package com.boardgamecharacterapi.controller;

import com.boardgamecharacterapi.services.*;
import com.boardgamecharacterapi.models.dto.CharactersDTO;
import com.boardgamecharacterapi.models.dto.GamesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final GamesService gamesService;
    private final CharactersService charactersService;
    private final SkillsService skillsService;
    private final CharactersTypeService charactersTypeService;
    private final CharactersTypeAssignementService charactersTypeAssignementService;

    @Autowired
    public SearchController(
            GamesService gamesService,
            CharactersService charactersService,
            SkillsService skillsService,
            CharactersTypeService charactersTypeService,
            CharactersTypeAssignementService charactersTypeAssignementService
    ) {
        this.gamesService = gamesService;
        this.charactersService = charactersService;
        this.skillsService = skillsService;
        this.charactersTypeService = charactersTypeService;
        this.charactersTypeAssignementService = charactersTypeAssignementService;
    }

    @GetMapping("/games/{gameId}/characters")
    public ResponseEntity<List<CharactersDTO>> searchCharacters(@PathVariable Long gameId) {
        // À implémenter : récupérer les personnages d'un jeu et les mapper en DTO
        List<CharactersDTO> characters = charactersService.getCharacterByGameId(gameId);
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/games")
    public ResponseEntity<List<GamesDTO>> searchGames() {
        List<GamesDTO> games = gamesService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<GamesDTO> getGameById(@PathVariable Long gameId) {
        GamesDTO game = gamesService.getGameById(gameId);
        return ResponseEntity.ok(game);
    }
}