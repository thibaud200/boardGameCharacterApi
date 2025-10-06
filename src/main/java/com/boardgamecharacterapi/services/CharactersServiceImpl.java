package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.models.Characters;
import com.boardgamecharacterapi.models.Games;
import com.boardgamecharacterapi.models.Skills;
import com.boardgamecharacterapi.models.Type;
import com.boardgamecharacterapi.models.dto.CharactersDTO;
import com.boardgamecharacterapi.repository.CharactersRepository;
import com.boardgamecharacterapi.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CharactersServiceImpl implements CharactersService {

    private final CharactersRepository charactersRepository;

    public CharactersServiceImpl(CharactersRepository charactersRepository) {
        this.charactersRepository = charactersRepository;
    }

    // =============================
    // 🔹 Lecture
    // =============================
    @Override
    @Transactional(readOnly = true)
    public List<CharactersDTO> getAllCharacters() {
        return charactersRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CharactersDTO> getCharacterById(Long id) {
        return charactersRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharactersDTO> getCharactersByGameId(Long gameId) {
        return charactersRepository.findByGameId(gameId).stream()
                .map(this::convertToDTO)
                .toList();
    }

    // =============================
    // 🔹 Création
    // =============================
    @Override
    public CharactersDTO saveCharacter(CharactersDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Character cannot be null");
        }

        Characters entity = convertToEntity(dto);
        Characters saved = charactersRepository.save(entity);
        return convertToDTO(saved);
    }

    // =============================
    // 🔹 Mise à jour
    // =============================
    @Override
    public CharactersDTO updateCharacter(CharactersDTO dto) {
        Characters existing = charactersRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Character not found with id: " + dto.getId()));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());

        Characters updated = charactersRepository.save(existing);
        return convertToDTO(updated);
    }

    // =============================
    // 🔹 Suppression
    // =============================
    @Override
    public void deleteCharacter(Long id) {
        if (!charactersRepository.existsById(id)) {
            throw new ResourceNotFoundException("Character not found with id: " + id);
        }
        charactersRepository.deleteById(id);
    }

    // =============================
    // 🔁 Conversions
    // =============================
    private CharactersDTO convertToDTO(Characters c) {
        return CharactersDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .gameId(c.getGame() != null ? c.getGame().getId() : null)
                .gameName(c.getGame() != null ? c.getGame().getTitle() : null)
                .typeNames(c.getType() != null ? c.getType().getName() : null)
                .skills(c.getSkills().stream()
                        .map(s -> new com.boardgamecharacterapi.models.dto.SkillsDTO(
                                s.getId(),
                                s.getName(),
                                s.getDescription()
                        ))
                        .toList())
                .build();
    }

    private Characters convertToEntity(CharactersDTO dto) {
        Characters c = new Characters();
        c.setId(dto.getId());
        c.setName(dto.getName());
        c.setDescription(dto.getDescription());

        if (dto.getGameId() != null) {
            Games g = new Games();
            g.setId(dto.getGameId());
            c.setGame(g);
        }

        if (dto.getTypeNames() != null) {
            Type t = new Type();
            t.setName(dto.getTypeNames());
            c.setType(t);
        }

        if (dto.getSkills() != null) {
            Set<Skills> skills = dto.getSkills().stream()
                    .map(s -> Skills.builder()
                            .id(s.getId())
                            .name(s.getName())
                            .description(s.getDescription())
                            .build())
                    .collect(Collectors.toSet());
            c.setSkills(skills);
        }

        return c;
    }
}
