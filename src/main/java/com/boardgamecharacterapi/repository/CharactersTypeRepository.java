package com.boardgamecharacterapi.repository;

import com.boardgamecharacterapi.models.CharactersType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharactersTypeRepository extends JpaRepository<CharactersType, Long> {

    // Méthode pour récupérer toutes les assignations d'un personnage
    List<CharactersType> findByCharacterId(Long characterId);

    // Méthode pour récupérer toutes les assignations d'un type
    List<CharactersType> findByCharactersTypeId(Long typeId);
}
