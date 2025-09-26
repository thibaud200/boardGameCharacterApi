package com.example.boardgamecharacterapi.repository;

import com.example.boardgamecharacterapi.model.CharacterTypeAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterTypeAssignmentRepository extends JpaRepository<CharacterTypeAssignment, Long> {
}
