package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.dto.GamesDTO;
import java.util.List;
import java.util.Optional;

public interface GamesService {
    List<GamesDTO> getAllGames();
    List<GamesDTO> getAllGamesDetails();
    List<GamesDTO> getAllGamesSimple();
    List<GamesDTO> getAllGamesChar();
    List<GamesDTO> searchGamesByTitle(String title, boolean includeCharacters);
    Optional<GamesDTO> getGameById(Long id, boolean includeCharacters);
    GamesDTO saveGame(GamesDTO gameDTO, boolean includeCharacters);
    GamesDTO updateGame(GamesDTO gameDTO, boolean includeCharacters);
    void deleteGame(Long id);
}