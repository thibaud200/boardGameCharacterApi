package com.boardgamecharacterapi.controller;

import com.boardgamecharacterapi.models.dto.CharactersDTO;
import com.boardgamecharacterapi.models.dto.SkillsDTO;
import com.boardgamecharacterapi.models.dto.TypeDTO;
import com.boardgamecharacterapi.services.CharactersService;
import com.boardgamecharacterapi.services.CharactersTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CharactersController.class)
class CharactersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CharactersService charactersService;

    @MockitoBean
    private CharactersTypeService charactersTypeService;

    // ---------------------------------------------------------
    // 🔹 GET /characters/list
    // ---------------------------------------------------------
    @Test
    void testGetAllCharacters() throws Exception {
        when(charactersService.getAllCharacters())
                .thenReturn(List.of(new CharactersDTO(4L, "Bishop", "Bishop", 1L, "Citadels", "Clerc", Collections.<SkillsDTO>emptyList())));

        mockMvc.perform(get("/characters/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bishop"))
                .andExpect(jsonPath("$[0].description").value("Bishop"))
                .andExpect(jsonPath("$[0].gameName").value("Citadels"))
                .andExpect(jsonPath("$[0].typeNames").value("Clerc"));
    }

    // ---------------------------------------------------------
    // 🔹 GET /characters/games/{gameId}/characters
    // ---------------------------------------------------------
    @Test
    void testGetCharactersByGame() throws Exception {
        when(charactersService.getCharactersByGameId(1L))
                .thenReturn(List.of(new CharactersDTO(4L, "Bishop", "Bishop", 1L, "Citadels", "Clerc", Collections.<SkillsDTO>emptyList())));

        mockMvc.perform(get("/characters/games/1/characters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bishop"))
                .andExpect(jsonPath("$[0].description").value("Bishop"))
                .andExpect(jsonPath("$[0].gameName").value("Citadels"))
                .andExpect(jsonPath("$[0].typeNames").value("Clerc"));
    }

    // ---------------------------------------------------------
    // 🔹 POST /characters
    // ---------------------------------------------------------
    @Test
    void testSaveCharacter() throws Exception {
        CharactersDTO response = new CharactersDTO(
                4L, "Bishop", "Bishop", 1L, "Citadels", "Clerc", Collections.<SkillsDTO>emptyList()
        );

        when(charactersService.saveCharacter(any())).thenReturn(response);

        mockMvc.perform(post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id":4,"name":"Bishop","description":"Bishop","gameId":1}
                                """))
                .andDo(result -> System.out.println(">>> Réponse brute : " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bishop"))
                .andExpect(jsonPath("$.description").value("Bishop"))
                .andExpect(jsonPath("$.gameName").value("Citadels"))
                .andExpect(jsonPath("$.typeNames").value("Clerc"));
    }

    // ---------------------------------------------------------
    // 🔹 PUT /characters
    // ---------------------------------------------------------
    @Test
    void testUpdateCharacter() throws Exception {
        CharactersDTO response = new CharactersDTO(
                4L, "Bishop", "Bishop", 1L, "Citadels", "Clerc", Collections.<SkillsDTO>emptyList()
        );

        when(charactersService.updateCharacter(any())).thenReturn(response);

        mockMvc.perform(put("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"id":4,"name":"Bishop","description":"Bishop","gameId":1}
                            """))
                .andDo(result -> System.out.println(">>> Réponse brute : " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bishop"))
                .andExpect(jsonPath("$.description").value("Bishop"))
                .andExpect(jsonPath("$.gameName").value("Citadels"))
                .andExpect(jsonPath("$.typeNames").value("Clerc"));
    }

    // ---------------------------------------------------------
    // 🔹DELETE /characters/characters/{charId}
    // ---------------------------------------------------------
    @Test
    void testDeleteCharacter() throws Exception {
        doNothing().when(charactersService).deleteCharacter(1L);

        mockMvc.perform(delete("/characters/characters/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    // ---------------------------------------------------------
    // 🔹 GET /characters/types
    // ---------------------------------------------------------
    @Test
    void testGetAllTypes() throws Exception {
        when(charactersTypeService.getAllCharactersType())
                .thenReturn(List.of(new TypeDTO(4L, "Bishop", "Bishop"), new TypeDTO(5L, "King", "King")));

        mockMvc.perform(get("/characters/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bishop"))
                .andExpect(jsonPath("$[1].name").value("King"));
    }
}
