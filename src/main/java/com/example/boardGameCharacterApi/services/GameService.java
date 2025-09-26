package com.example.boardgamecharacterapi.services;

import com.example.boardgamecharacterapi.model.Game;
import java.util.List;
import java.util.Optional;

public interface GameService {
    List<Game> getAllGames();
    Optional<Game> getGameById(Long id);
    Game saveGame(Game game);
    void deleteGame(Long id);
}
