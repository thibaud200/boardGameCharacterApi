package com.example.boardgamecharacterapi.repository;

import com.example.boardgamecharacterapi.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
}
