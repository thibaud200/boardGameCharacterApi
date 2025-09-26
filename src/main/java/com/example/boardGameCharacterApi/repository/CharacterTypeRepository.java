package com.example.boardgamecharacterapi.repository;

import com.example.boardgamecharacterapi.model.CharacterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterTypeRepository extends JpaRepository<CharacterType, Long> {
}
