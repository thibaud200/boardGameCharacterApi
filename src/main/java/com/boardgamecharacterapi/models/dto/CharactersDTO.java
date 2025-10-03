package com.boardgamecharacterapi.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharactersDTO {

    private Long id;

    @NotBlank(message = "Character name is required")
    @Size(min = 2, max = 100, message = "Character name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Game ID is required")
    private Long gameId;

    private String gameName; // Nom du jeu pour affichage

    private String typeNames;   // ou List<CharactersTypeDTO>
    private List<SkillsDTO> skills;
}