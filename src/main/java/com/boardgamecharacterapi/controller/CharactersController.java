package com.boardgamecharacterapi.controller;

import com.boardgamecharacterapi.models.dto.CharactersDTO;
import com.boardgamecharacterapi.models.dto.SkillsDTO;
import com.boardgamecharacterapi.models.dto.TypeDTO;
import com.boardgamecharacterapi.services.CharactersService;
import com.boardgamecharacterapi.services.CharactersTypeService;
import com.boardgamecharacterapi.services.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/characters")
public class CharactersController {

    private final CharactersService charactersService;
    private final SkillsService skillsService;
    private final CharactersTypeService charactersTypeService;

    @Autowired
    public CharactersController(
            CharactersService charactersService,
            SkillsService skillsService,
            CharactersTypeService charactersTypeService
    ) {
        this.charactersService = charactersService;
        this.skillsService = skillsService;
        this.charactersTypeService = charactersTypeService;
    }

    // 🔹 Récupérer tous les personnages
    @GetMapping("/list")
    public ResponseEntity<List<CharactersDTO>> getAllCharacters() {
        List<CharactersDTO> characters = charactersService.getAllCharacters();
        return ResponseEntity.ok(characters);
    }

    // 🔹 Récupérer les personnages d’un jeu
    @GetMapping("/games/{gameId}/characters")
    public ResponseEntity<List<CharactersDTO>> getCharactersByGame(@PathVariable Long gameId) {
        List<CharactersDTO> characters = charactersService.getCharactersByGameId(gameId);
        return ResponseEntity.ok(characters);
    }

    @PostMapping
    public ResponseEntity<CharactersDTO> saveCharacters(@RequestBody CharactersDTO charactersDTO) {
        CharactersDTO character = charactersService.saveCharacter(charactersDTO);
        return ResponseEntity.ok(character);
    }

    @PutMapping
    public ResponseEntity<CharactersDTO> updateCharacters(@RequestBody CharactersDTO charactersDTO) {
        CharactersDTO character = charactersService.updateCharacter(charactersDTO);
        return ResponseEntity.ok(character);
    }

    @DeleteMapping("/characters/{charId}")
    public ResponseEntity<Long> deleteCharacters(@PathVariable Long charId) {
        charactersService.deleteCharacter(charId);
        return ResponseEntity.ok(charId);
    }

    // 🔹 Récupérer tous les types de personnages
    @GetMapping("/types")
    public ResponseEntity<List<TypeDTO>> getAllTypes() {
        List<TypeDTO> types = charactersTypeService.getAllCharactersType();
        return ResponseEntity.ok(types);
    }

    // 🔹 Récupérer tous les skills
    @GetMapping("/skills")
    public ResponseEntity<List<SkillsDTO>> getAllSkills() {
        List<SkillsDTO> skills = skillsService.getAllSkills();
        return ResponseEntity.ok(skills);
    }
}
