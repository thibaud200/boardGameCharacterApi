package com.boardgamecharacterapi.services;

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
        return gamesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GamesDTO> getGameById(Long id) {
        return gamesRepository.findById(id);
    }

    @Override
    public GamesDTO saveGame(GamesDTO game) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }
        return gamesRepository.save(game);
    }

    @Override
    public GamesDTO updateGame(Long id, GamesDTO game) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }

        return gamesRepository.findById(id)
                .map(existingGame -> {
                    // Mise à jour des champs (adapter selon vos attributs)
                    if (game.getTitle() != null) {
                        existingGame.setTitle(game.getTitle());
                    }
                    if (game.getDescription() != null) {
                        existingGame.setDescription(game.getDescription());
                    }
                    if (game.getReleaseYear() != null) {
                        existingGame.setReleaseYear(game.getReleaseYear());
                    }
                    return gamesRepository.save(existingGame);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + id));
    }

    @Override
    public void deleteGame(Long id) {
        if (!gamesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Game not found with id: " + id);
        }
        gamesRepository.deleteById(id);
    }
}