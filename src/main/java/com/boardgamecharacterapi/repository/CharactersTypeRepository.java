package com.boardgamecharacterapi.repository;

import com.boardgamecharacterapi.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharactersTypeRepository extends JpaRepository<Type, Long> {

    // Si jamais tu veux récupérer un type à partir de son nom :
    Type findByName(String name);
}
