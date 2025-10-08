package com.boardgamecharacterapi.repository;

import com.boardgamecharacterapi.models.Characters;
import com.boardgamecharacterapi.models.Games;
import com.boardgamecharacterapi.models.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CharacterRepositoryTest {

    @Autowired
    private CharactersRepository repository;

    @Test
    void testSaveCharacter() {
        Games game = new Games();
        game.setTitle("Mon jeu de test");

        Type type = new Type();
        type.setName("Guerrier");

        Characters character = Characters.builder()
                .name("Personnage")
                .description("Test description")
                .game(game)
                .type(type)
                .build();

        Characters saved = repository.save(character);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getGame()).isNotNull();
    }
}
