package com.boardgamecharacterapi.controller;

import com.boardgamecharacterapi.models.dto.GamesDTO;
import com.boardgamecharacterapi.services.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/games")
public class GamesController {

    private final GamesService gamesService;


    @Autowired
    public GamesController(
            GamesService gamesService
    ) {
        this.gamesService = gamesService;
    }

    // 🔹 Récupérer tous les jeux
    @GetMapping("/list")
    public ResponseEntity<List<GamesDTO>> getAllGames() {
        List<GamesDTO> games = gamesService.getAllGames();
        return ResponseEntity.ok(games);
    }

    // 🔹 Récupérer tous les jeux
    @GetMapping("/detaillist")
    public ResponseEntity<List<GamesDTO>> getAllGamesDetails() {
        List<GamesDTO> games = gamesService.getAllGamesDetails();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/simplelist")
    public ResponseEntity<List<GamesDTO>> getAllGamesSimple() {
        List<GamesDTO> games = gamesService.getAllGamesSimple();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/gamecharlist")
    public ResponseEntity<List<GamesDTO>> getAllGamesChar() {
        List<GamesDTO> games = gamesService.getAllGamesChar();
        return ResponseEntity.ok(games);
    }

    // 🔹 Récupérer un jeu par ID
    @GetMapping("/games/{gameId}")
    public ResponseEntity<GamesDTO> getGameById(@PathVariable Long gameId) {
        return gamesService.getGameById(gameId, true)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/games/search")
    public ResponseEntity<List<GamesDTO>> searchGamesByTitle(@RequestParam String title) {
        List<GamesDTO> games = gamesService.searchGamesByTitle(title, false);
        return ResponseEntity.ok(games);
    }

    @PostMapping
    public ResponseEntity<GamesDTO> saveGame(@RequestBody GamesDTO gameDTO) {
        GamesDTO game = gamesService.saveGame(gameDTO, true);
        return ResponseEntity.ok(game);
    }

    @PutMapping
    public ResponseEntity<GamesDTO> updateGame(@RequestBody GamesDTO gameDTO) {
        GamesDTO game = gamesService.updateGame(gameDTO, true);
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("/games/{gameId}")
    public ResponseEntity<Long> deleteGame(@PathVariable Long gameId) {
        gamesService.deleteGame(gameId);
        return ResponseEntity.ok(gameId);
    }
}
