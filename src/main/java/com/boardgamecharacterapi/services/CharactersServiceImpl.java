package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.Characters;
import com.boardgamecharacterapi.repository.CharactersRepository;
import com.boardgamecharacterapi.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CharactersServiceImpl implements CharactersService {

    private final CharactersRepository charactersRepository;

    // Constructor injection (meilleure pratique que @Autowired sur le champ)
    public CharactersServiceImpl(CharactersRepository charactersRepository) {
        this.charactersRepository = charactersRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Characters> getAllCharacters() {
        return charactersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Characters> getCharacterById(Long id) {
        return charactersRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Characters> getCharactersByGameId(Long gameId) {
        // Cette méthode nécessite une requête custom dans le repository
        return charactersRepository.findByGameId(gameId);
    }

    @Override
    public Characters saveCharacter(Characters character) {
        if (character == null) {
            throw new IllegalArgumentException("Character cannot be null");
        }
        return charactersRepository.save(character);
    }

    @Override
    public Characters updateCharacter(Long id, Characters character) {
        if (character == null) {
            throw new IllegalArgumentException("Character cannot be null");
        }

        return charactersRepository.findById(id)
                .map(existingCharacter -> {
                    // Mise à jour des champs (adapter selon vos attributs)
                    if (character.getName() != null) {
                        existingCharacter.setName(character.getName());
                    }
                    if (character.getDescription() != null) {
                        existingCharacter.setDescription(character.getDescription());
                    }
                    if (character.getGame() != null) {
                        existingCharacter.setGame(character.getGame());
                    }
                    return charactersRepository.save(existingCharacter);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Character not found with id: " + id));
    }

    @Override
    public void deleteCharacter(Long id) {
        if (!charactersRepository.existsById(id)) {
            throw new ResourceNotFoundException("Character not found with id: " + id);
        }
        charactersRepository.deleteById(id);
    }
}