package com.boardgamecharacterapi.controller;

import com.boardgamecharacterapi.models.dto.SkillsDTO;
import com.boardgamecharacterapi.services.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/skills")
public class SkillsController {

    private final SkillsService skillsService;

    @Autowired
    public SkillsController(
            SkillsService skillsService
    ) {
        this.skillsService = skillsService;
    }


    // 🔹 Récupérer tous les skills
    @GetMapping("/skills")
    public ResponseEntity<List<SkillsDTO>> getAllSkills() {
        List<SkillsDTO> skills = skillsService.getAllSkills();
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/skills/{skillId}")
    public ResponseEntity<SkillsDTO> getGameById(@PathVariable Long skillId) {
        return skillsService.getSkillsById(skillId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/skills/search")
    public ResponseEntity<List<SkillsDTO>> searchGamesByTitle(@RequestParam String name) {
        List<SkillsDTO> skills = skillsService.searchSkillByName(name);
        return ResponseEntity.ok(skills);
    }

    @PostMapping
    public ResponseEntity<SkillsDTO> saveSkill(@RequestBody SkillsDTO skillsDTO) {
        SkillsDTO skills = skillsService.saveSkill(skillsDTO);
        return ResponseEntity.ok(skills);
    }

    @PutMapping
    public ResponseEntity<SkillsDTO> updateSkill(@RequestBody SkillsDTO skillsDTO) {
        SkillsDTO skills = skillsService.updateSkill(skillsDTO);
        return ResponseEntity.ok(skills);
    }

    @DeleteMapping("/skills/{skillId}")
    public ResponseEntity<Long> deleteSkill(@PathVariable Long skillId) {
        skillsService.deleteSkill(skillId);
        return ResponseEntity.ok(skillId);
    }
}
