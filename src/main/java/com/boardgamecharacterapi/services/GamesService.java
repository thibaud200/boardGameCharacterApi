package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.dto.GamesDTO;
import java.util.List;
import java.util.Optional;

public interface GamesService {
    List<GamesDTO> getAllGames();
    List<GamesDTO> searchGamesByTitle(String title);
    Optional<GamesDTO> getGameById(Long id);
    GamesDTO saveGame(GamesDTO gameDTO);
    GamesDTO updateGame(GamesDTO gameDTO);
    void deleteGame(Long id);
}