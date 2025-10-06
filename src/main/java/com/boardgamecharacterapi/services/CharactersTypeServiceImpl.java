package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.ResourceNotFoundException;
import com.boardgamecharacterapi.models.Type;
import com.boardgamecharacterapi.models.dto.TypeDTO;
import com.boardgamecharacterapi.repository.CharactersTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CharactersTypeServiceImpl implements CharactersTypeService {

    private final CharactersTypeRepository charactersTypeRepository;

    public CharactersTypeServiceImpl(CharactersTypeRepository charactersTypeRepository) {
        this.charactersTypeRepository = charactersTypeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeDTO> getAllCharactersType() {
        return charactersTypeRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public Optional<TypeDTO> getCharacterTypeById(Long id) {
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public Optional<TypeDTO> getCharacterTypeByName(String name) {
        Type type = charactersTypeRepository.findByName(name);
        return Optional.ofNullable(type)
                .map(this::convertToDTO);
    }

    @Override
    public TypeDTO saveCharacterType(TypeDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }
        Type entity = convertToEntity(dto);
        Type saved = charactersTypeRepository.save(entity);
        return convertToDTO(saved);
    }

    @Override
    public TypeDTO updateCharacterType(Long id, TypeDTO dto) {
        Type existing = charactersTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CharacterType not found with id: " + id));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());

        Type updated = charactersTypeRepository.save(existing);
        return convertToDTO(updated);
    }

    @Override
    public void deleteCharacterType(Long id) {
        if (!charactersTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("CharacterType not found with id: " + id);
        }
        charactersTypeRepository.deleteById(id);
    }

    // 🔁 Conversion Entity → DTO
    private TypeDTO convertToDTO(Type type) {
        return TypeDTO.builder()
                .id(type.getId())
                .name(type.getName())
                .description(type.getDescription())
                .build();
    }

    // 🔁 Conversion DTO → Entity
    private Type convertToEntity(TypeDTO dto) {
        return Type.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
