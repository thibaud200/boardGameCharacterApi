package com.boardgamecharacterapi.repository;

import com.boardgamecharacterapi.models.Skills;
import com.boardgamecharacterapi.models.dto.SkillsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long> {
    List<Skills> findSkillsByCharacters_Id(Long characterId);
}
