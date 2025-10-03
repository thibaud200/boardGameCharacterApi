package com.boardgamecharacterapi.repository;

import com.boardgamecharacterapi.models.dto.TypeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharactersTypeRepository extends JpaRepository<TypeDTO, Long> {

    // Méthode pour récupérer toutes les assignations d'un personnage
    List<TypeDTO> findByCharacterId(Long characterId);

    // Méthode pour récupérer toutes les assignations d'un type
    List<TypeDTO> findByCharactersTypeId(Long typeId);
}
