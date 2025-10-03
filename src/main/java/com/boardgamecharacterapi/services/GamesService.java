package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.dto.GamesDTO;
import java.util.List;
import java.util.Optional;

public interface GamesService {
    List<GamesDTO> getAllGames();
    Optional<GamesDTO> getGameById(Long id);
    GamesDTO saveGame(GamesDTO game);
    GamesDTO updateGame(Long id, GamesDTO game);
    void deleteGame(Long id);
}