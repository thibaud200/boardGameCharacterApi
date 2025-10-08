package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.ResourceNotFoundException;
import com.boardgamecharacterapi.models.Characters;
import com.boardgamecharacterapi.models.Games;
import com.boardgamecharacterapi.models.Skills;
import com.boardgamecharacterapi.models.dto.GamesDTO;
import com.boardgamecharacterapi.models.dto.SkillsDTO;
import com.boardgamecharacterapi.repository.CharactersRepository;
import com.boardgamecharacterapi.repository.GamesRepository;
import com.boardgamecharacterapi.repository.SkillsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GamesServiceImplTest {

    @Mock
    private GamesRepository gamesRepository;

    @InjectMocks
    private GamesServiceImpl gamesService;

    private Games game;
    private GamesDTO dto;

    @BeforeEach
    void setUp() {
        game = Games.builder()
                .id(1L)
                .title("Catan")
                .description("Catan Game")
                .build();

        dto = GamesDTO.builder()
                .id(1L)
                .title("Catan")
                .description("Catan Game")
                .build();
    }

    @Test
    void getAllGames_ShouldReturnList() {
        when(gamesRepository.findAll()).thenReturn(List.of(game));

        List<GamesDTO> result = gamesService.getAllGames();

        assertEquals(1, result.size());
        assertEquals("Catan", result.getFirst().getTitle());
        verify(gamesRepository, times(1)).findAll();
    }

    @Test
    void getGamesById_ShouldReturnDto_WhenFound() {
        when(gamesRepository.findById(1L)).thenReturn(Optional.of(game));

        Optional<GamesDTO> result = gamesService.getGameById(1L, false);

        assertTrue(result.isPresent());
        assertEquals("Catan", result.get().getTitle());
        verify(gamesRepository).findById(1L);
    }

    @Test
    void getGameById_ShouldReturnEmpty_WhenNotFound() {
        when(gamesRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<GamesDTO> result = gamesService.getGameById(2L, false);

        assertTrue(result.isEmpty());
    }

    @Test
    void saveGame_ShouldPersistAndReturnDto() {
        when(gamesRepository.save(any(Games.class))).thenReturn(game);

        GamesDTO saved = gamesService.saveGame(dto, false);

        assertEquals("Catan", saved.getTitle());
        verify(gamesRepository).save(any(Games.class));
    }

    @Test
    void saveGame_ShouldThrow_WhenNull() {
        assertThrows(IllegalArgumentException.class, () -> gamesService.saveGame(null, false));
    }

    @Test
    void updateGame_ShouldUpdate_WhenFound() {
        Games updatedGame = Games.builder()
                .id(1L)
                .title("Inferno")
                .description("Updated desc")
                .build();

        when(gamesRepository.findById(1L)).thenReturn(Optional.of(game));
        when(gamesRepository.save(any(Games.class))).thenReturn(updatedGame);

        GamesDTO updateDto = GamesDTO.builder()
                .id(1L)
                .title("Inferno")
                .description("Updated desc")
                .build();

        GamesDTO result = gamesService.updateGame(updateDto, false);

        assertEquals("Inferno", result.getTitle());
        verify(gamesRepository).save(any(Games.class));
    }

    @Test
    void updateGame_ShouldThrow_WhenNotFound() {
        when(gamesRepository.findById(999L)).thenReturn(Optional.empty());

        GamesDTO result = GamesDTO.builder().id(999L).build();

        assertThrows(ResourceNotFoundException.class, () -> gamesService.updateGame(result, false));
    }

    @Test
    void deleteGame_ShouldDelete_WhenExists() {
        when(gamesRepository.existsById(1L)).thenReturn(true);

        gamesService.deleteGame(1L);

        verify(gamesRepository).deleteById(1L);
    }

    @Test
    void deleteGame_ShouldThrow_WhenNotFound() {
        when(gamesRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> gamesService.deleteGame(1L));
    }

    @Test
    void searchSkillByName_ShouldReturnResult_WhenFound() {
        when(gamesRepository.findByTitleContainingIgnoreCase("Catan")).thenReturn(List.of(game));

        List<GamesDTO> result = gamesService.searchGamesByTitle("Catan", false);

        assertFalse(result.isEmpty());
        assertEquals("Catan", result.getFirst().getTitle());
    }
}
