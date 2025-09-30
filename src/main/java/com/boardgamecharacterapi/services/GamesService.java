package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.Games;
import java.util.List;
import java.util.Optional;

public interface GamesService {
    List<Games> getAllGames();
    Optional<Games> getGameById(Long id);
    Games saveGame(Games game);
    Games updateGame(Long id, Games game);
    void deleteGame(Long id);
}