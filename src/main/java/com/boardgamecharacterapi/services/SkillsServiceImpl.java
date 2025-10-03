package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.boardgamecharacterapi.repository.SkillsRepository;
import com.boardgamecharacterapi.models.dto.SkillsDTO;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SkillsServiceImpl implements SkillsService {

    private final SkillsRepository skillsRepository;

    // Constructor injection (meilleure pratique que @Autowired sur le champ)
    public SkillsServiceImpl(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillsDTO> getAllSkills() {
        return skillsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SkillsDTO> getSkillsById(Long id) {
        return skillsRepository.findById(id);
    }

    @Override
    public SkillsDTO saveSkill(SkillsDTO skill) {
        if (skill == null) {
            throw new IllegalArgumentException("Skill cannot be null");
        }
        return skillsRepository.save(skill);
    }

    @Override
    public SkillsDTO updateSkill(Long id, SkillsDTO skill) {
        if (skill == null) {
            throw new IllegalArgumentException("Skill cannot be null");
        }

        return skillsRepository.findById(id)
                .map(existingSkill -> {
                    // Mise à jour des champs (adapter selon vos attributs)
                    if (skill.getName() != null) {
                        existingSkill.setName(skill.getName());
                    }
                    if (skill.getDescription() != null) {
                        existingSkill.setDescription(skill.getDescription());
                    }
                    return skillsRepository.save(existingSkill);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));
    }

    @Override
    public void deleteSkill(Long id) {
        if (!skillsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill not found with id: " + id);
        }
        skillsRepository.deleteById(id);
    }
}
