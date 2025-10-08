package com.boardgamecharacterapi.controller;

import com.boardgamecharacterapi.services.CharactersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharactersController.class)
class CharactersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CharactersService charactersService;
    @Test
    void testGetCharacters() throws Exception {
        mockMvc.perform(get("/characters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Nom"));
    }
}
