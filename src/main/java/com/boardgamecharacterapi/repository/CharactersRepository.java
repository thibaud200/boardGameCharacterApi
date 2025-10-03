package com.boardgamecharacterapi.repository;

import com.boardgamecharacterapi.models.dto.CharactersDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharactersRepository extends JpaRepository<CharactersDTO, Long> {

    // Méthode pour récupérer tous les personnages d'un jeu spécifique
    List<CharactersDTO> findByGameId(Long gameId);
}