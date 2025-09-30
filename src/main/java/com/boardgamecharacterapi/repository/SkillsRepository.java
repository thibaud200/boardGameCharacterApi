package com.boardgamecharacterapi.repository;

import com.boardgamecharacterapi.models.Characters;
import com.boardgamecharacterapi.models.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long> {
    List<Skills> findSkillsByCharacter(Long characterId);
}
