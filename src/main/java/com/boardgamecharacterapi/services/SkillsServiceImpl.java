package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.ResourceNotFoundException;
import com.boardgamecharacterapi.models.Skills;
import com.boardgamecharacterapi.models.dto.SkillsDTO;
import com.boardgamecharacterapi.repository.SkillsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SkillsServiceImpl implements SkillsService {

    private final SkillsRepository skillsRepository;

    public SkillsServiceImpl(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillsDTO> getAllSkills() {
        return skillsRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SkillsDTO> getSkillsById(Long id) {
        return skillsRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public SkillsDTO saveSkill(SkillsDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Skill cannot be null");
        }
        Skills entity = convertToEntity(dto);
        Skills saved = skillsRepository.save(entity);
        return convertToDTO(saved);
    }

    @Override
    public SkillsDTO updateSkill(Long id, SkillsDTO dto) {
        Skills existing = skillsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());

        Skills updated = skillsRepository.save(existing);
        return convertToDTO(updated);
    }

    @Override
    public void deleteSkill(Long id) {
        if (!skillsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill not found with id: " + id);
        }
        skillsRepository.deleteById(id);
    }

    // 🔁 Conversions
    private SkillsDTO convertToDTO(Skills skill) {
        return SkillsDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .description(skill.getDescription())
                .build();
    }

    private Skills convertToEntity(SkillsDTO dto) {
        return Skills.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
