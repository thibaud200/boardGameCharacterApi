package com.boardgamecharacterapi.repository;

import com.boardgamecharacterapi.models.Characters;
import com.boardgamecharacterapi.models.Games;
import com.boardgamecharacterapi.models.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CharacterRepositoryTest {

    @Autowired
    private CharactersRepository charactersrepository;
    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private CharactersTypeRepository typesRepository;

    @Test
    void testSaveCharacter() {
        Games game = new Games();
        game.setTitle("Catan");
        game.setDescription("jeu Catan");
        game.setReleaseYear(2015);
        game = gamesRepository.save(game);

        Type type = new Type();
        type.setName("Bishop");
        type = typesRepository.save(type);

        Characters character = Characters.builder()
                .name("Personnage")
                .description("Test description")
                .game(game)
                .type(type)
                .build();

        Characters saved = charactersrepository.save(character);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getGame()).isNotNull();
    }
}
