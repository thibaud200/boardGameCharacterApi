package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.CharactersType;
import com.boardgamecharacterapi.repository.CharactersTypeRepository;
import com.boardgamecharacterapi.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CharactersTypeServiceImpl implements CharactersTypeService {

    private final CharactersTypeRepository charactersTypeRepository;

    // Constructor injection (meilleure pratique que @Autowired sur le champ)
    public CharactersTypeServiceImpl(CharactersTypeRepository charactersTypeRepository) {
        this.charactersTypeRepository = charactersTypeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharactersType> getAllCharactersType() {
        return charactersTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CharactersType> getCharacterTypeById(Long id) {
        return charactersTypeRepository.findById(id);
    }

    @Override
    public CharactersType saveCharacterType(CharactersType characterType) {
        if (characterType == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }
        return charactersTypeRepository.save(characterType);
    }

    @Override
    public CharactersType updateCharacterType(Long id, CharactersType characterType) {
        if (characterType == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }

        return charactersTypeRepository.findById(id)
                .map(existingType -> {
                    // Mise à jour des champs (adapter selon vos attributs)
                    if (characterType.getName() != null) {
                        existingType.setName(characterType.getName());
                    }
                    if (characterType.getDescription() != null) {
                        existingType.setDescription(characterType.getDescription());
                    }
                    return charactersTypeRepository.save(existingType);
                })
                .orElseThrow(() -> new ResourceNotFoundException("CharacterType not found with id: " + id));
    }

    @Override
    public void deleteCharacterType(Long id) {
        if (!charactersTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("CharacterType not found with id: " + id);
        }
        charactersTypeRepository.deleteById(id);
    }
}