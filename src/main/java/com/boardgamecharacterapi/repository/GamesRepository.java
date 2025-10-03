package com.boardgamecharacterapi.repository;

import com.boardgamecharacterapi.models.dto.GamesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamesRepository extends JpaRepository<GamesDTO, Long> {
    // Tu peux ajouter des méthodes personnalisées ici, ex :
    List<GamesDTO> findByTitleContainingIgnoreCase(String title);
}
