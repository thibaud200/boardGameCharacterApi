package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.boardgamecharacterapi.repository.SkillsRepository;
import com.boardgamecharacterapi.models.Skills;
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
    public List<Skills> getAllSkills() {
        return skillsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Skills> getSkillsById(Long id) {
        return skillsRepository.findById(id);
    }

    @Override
    public Skills saveSkill(Skills skill) {
        if (skill == null) {
            throw new IllegalArgumentException("Skill cannot be null");
        }
        return skillsRepository.save(skill);
    }

    @Override
    public Skills updateSkill(Long id, Skills skill) {
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
                    if (skill.getLevel() != null) {
                        existingSkill.setLevel(skill.getLevel());
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
