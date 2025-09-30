package com.boardgamecharacterapi.repository;

import com.boardgamecharacterapi.models.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Games, Long> {
    // Tu peux ajouter des méthodes personnalisées ici, ex :
    // List<Game> findByTitleContainingIgnoreCase(String title);
}
