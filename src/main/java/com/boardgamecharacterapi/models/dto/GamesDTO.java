package com.boardgamecharacterapi.models.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.boardgamecharacterapi.models.Characters;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GamesDTO {
    private Long id;

    @NotBlank(message = "Skill name is required")
    @Size(min = 2, max = 100, message = "Skill name must be between 2 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Size(max = 4, message = "Year of release must be 4 characters")
    private Long releaseYear;

    @Max(value = 50, message = "maximum Character name must be at most 50")
    private List<Characters> characters;
}
