package com.example.boardgamecharacterapi.repository;

import com.example.boardgamecharacterapi.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    // Tu peux ajouter des méthodes personnalisées ici, ex :
    // List<Game> findByTitleContainingIgnoreCase(String title);
}
