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
    CharactersService charactersService;

    // Constructor injection (meilleure pratique que @Autowired sur le champ)
    public GamesServiceImpl(GamesRepository gamesRepository, CharactersService charactersService) {
        this.gamesRepository = gamesRepository;
        this.charactersService = charactersService;
    }
    private List<GamesDTO> getAllGamesInternal(boolean includeCharacters) {
        return gamesRepository.findAll().stream()
                .map(game -> convertToDTO(game, includeCharacters))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GamesDTO> getAllGames() {
        // version simple sans personnages
        return getAllGamesInternal(false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GamesDTO> getAllGamesDetails() {
        // version avec personnages
        return getAllGamesInternal(true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GamesDTO> getAllGamesSimple() {
        // même logique, on choisit ce qu’on veut afficher
        return getAllGamesInternal(false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GamesDTO> getAllGamesChar() {
        // si tu veux uniquement ceux liés à un personnage
        return getAllGamesInternal(true);
    }

    @Transactional(readOnly = true)
    public List<GamesDTO> searchGamesByTitle(String title, boolean includeCharacters) {
        return gamesRepository.findByTitleContainingIgnoreCase(title).stream()
            .map(game -> convertToDTO(game, includeCharacters))
            .toList();
    }

    @Transactional(readOnly = true)
    public Optional<GamesDTO> getGameById(Long id, boolean includeCharacters) {
        return gamesRepository.findById(id)
                .map(game -> convertToDTO(game, includeCharacters));
    }

    public GamesDTO saveGame(GamesDTO gameDTO, boolean includeCharacters) {
        if (gameDTO == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }
        Games entity = convertToEntity(gameDTO);
        Games saved = gamesRepository.save(entity);
        return convertToDTO(saved, includeCharacters);
    }

    public GamesDTO updateGame(GamesDTO gameDTO, boolean includeCharacters) {
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
        return convertToDTO(updated, includeCharacters);
    }

    public void deleteGame(Long id) {
        if (!gamesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Game not found with id: " + id);
        }
        gamesRepository.deleteById(id);
    }

    private GamesDTO convertToDTO(Games game, boolean includeCharacters) {
        GamesDTO.GamesDTOBuilder builder = GamesDTO.builder()
            .id(game.getId())
            .title(game.getTitle())
            .description(game.getDescription())
            .releaseYear(game.getReleaseYear());

        if (includeCharacters) {
            builder.characters(charactersService.getAllCharacters());
        }

        // On construit l’objet final
        return builder.build();
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