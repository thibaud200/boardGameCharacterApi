package com.boardgamecharacterapi.repository;

import com.boardgamecharacterapi.models.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long> {
    List<Skills> findSkillsByCharacters_Id(Long characterId);
    Optional<Skills> findByNameIgnoreCase(String name);
}
