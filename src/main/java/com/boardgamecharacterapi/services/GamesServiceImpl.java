package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.Games;
import com.boardgamecharacterapi.models.dto.GamesDTO;
import com.boardgamecharacterapi.repository.GamesRepository;
import com.boardgamecharacterapi.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GamesServiceImpl implements GamesService {

    private final GamesRepository gamesRepository;

    // Constructor injection (meilleure pratique que @Autowired sur le champ)
    public GamesServiceImpl(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GamesDTO> getAllGames() {
        return gamesRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GamesDTO> searchGamesByTitle(String title) {
        return gamesRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GamesDTO> getGameById(Long id) {
        return gamesRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public GamesDTO saveGame(GamesDTO gameDTO) {
        if (gameDTO == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }
        Games entity = convertToEntity(gameDTO);
        Games saved = gamesRepository.save(entity);
        return convertToDTO(saved);
    }

    @Override
    public GamesDTO updateGame(GamesDTO gameDTO) {
        if (gameDTO == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }

        Games existingGame = gamesRepository.findById(gameDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + gameDTO.getId()));

        // Mise à jour des champs
        if (gameDTO.getTitle() != null) existingGame.setTitle(gameDTO.getTitle());
        if (gameDTO.getDescription() != null) existingGame.setDescription(gameDTO.getDescription());
        if (gameDTO.getReleaseYear() != null) existingGame.setReleaseYear(gameDTO.getReleaseYear());

        Games updated = gamesRepository.save(existingGame);
        return convertToDTO(updated);
    }

    @Override
    public void deleteGame(Long id) {
        if (!gamesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Game not found with id: " + id);
        }
        gamesRepository.deleteById(id);
    }

    private GamesDTO convertToDTO(Games game) {
        return GamesDTO.builder()
                .id(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .releaseYear(game.getReleaseYear())
                .build();
    }

    private Games convertToEntity(GamesDTO dto) {
        return Games.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .releaseYear(dto.getReleaseYear())
                .build();
    }
}