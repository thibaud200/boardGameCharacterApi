package com.boardgamecharacterapi.services;

import com.boardgamecharacterapi.ResourceNotFoundException;
import com.boardgamecharacterapi.models.Skills;
import com.boardgamecharacterapi.models.dto.SkillsDTO;
import com.boardgamecharacterapi.repository.SkillsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillsServiceImplTest {

    @Mock
    private SkillsRepository skillsRepository;

    @InjectMocks
    private SkillsServiceImpl skillsService;

    private Skills skill;
    private SkillsDTO dto;

    @BeforeEach
    void setUp() {
        skill = Skills.builder()
                .id(1L)
                .name("Fireball")
                .description("Launches a fireball")
                .build();

        dto = SkillsDTO.builder()
                .id(1L)
                .name("Fireball")
                .description("Launches a fireball")
                .build();
    }

    @Test
    void getAllSkills_ShouldReturnList() {
        when(skillsRepository.findAll()).thenReturn(List.of(skill));

        List<SkillsDTO> result = skillsService.getAllSkills();

        assertEquals(1, result.size());
        assertEquals("Fireball", result.getFirst().getName());
        verify(skillsRepository, times(1)).findAll();
    }

    @Test
    void getSkillsById_ShouldReturnDto_WhenFound() {
        when(skillsRepository.findById(1L)).thenReturn(Optional.of(skill));

        Optional<SkillsDTO> result = skillsService.getSkillsById(1L);

        assertTrue(result.isPresent());
        assertEquals("Fireball", result.get().getName());
        verify(skillsRepository).findById(1L);
    }

    @Test
    void getSkillsById_ShouldReturnEmpty_WhenNotFound() {
        when(skillsRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<SkillsDTO> result = skillsService.getSkillsById(2L);

        assertTrue(result.isEmpty());
    }

    @Test
    void saveSkill_ShouldPersistAndReturnDto() {
        when(skillsRepository.save(any(Skills.class))).thenReturn(skill);

        SkillsDTO saved = skillsService.saveSkill(dto);

        assertEquals("Fireball", saved.getName());
        verify(skillsRepository).save(any(Skills.class));
    }

    @Test
    void saveSkill_ShouldThrow_WhenNull() {
        assertThrows(IllegalArgumentException.class, () -> skillsService.saveSkill(null));
    }

    @Test
    void updateSkill_ShouldUpdate_WhenFound() {
        Skills updatedSkill = Skills.builder()
                .id(1L)
                .name("Inferno")
                .description("Updated desc")
                .build();

        when(skillsRepository.findById(1L)).thenReturn(Optional.of(skill));
        when(skillsRepository.save(any(Skills.class))).thenReturn(updatedSkill);

        SkillsDTO updateDto = SkillsDTO.builder()
                .id(1L)
                .name("Inferno")
                .description("Updated desc")
                .build();

        SkillsDTO result = skillsService.updateSkill(updateDto);

        assertEquals("Inferno", result.getName());
        verify(skillsRepository).save(any(Skills.class));
    }

    @Test
    void updateSkill_ShouldThrow_WhenNotFound() {
        when(skillsRepository.findById(999L)).thenReturn(Optional.empty());

        SkillsDTO result = SkillsDTO.builder().id(999L).build();

        assertThrows(ResourceNotFoundException.class, () -> skillsService.updateSkill(result));
    }

    @Test
    void deleteSkill_ShouldDelete_WhenExists() {
        when(skillsRepository.existsById(1L)).thenReturn(true);

        skillsService.deleteSkill(1L);

        verify(skillsRepository).deleteById(1L);
    }

    @Test
    void deleteSkill_ShouldThrow_WhenNotFound() {
        when(skillsRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> skillsService.deleteSkill(1L));
    }

    @Test
    void searchSkillByName_ShouldReturnResult_WhenFound() {
        when(skillsRepository.findByNameIgnoreCase("fireball")).thenReturn(Optional.of(skill));

        Optional<SkillsDTO> result = skillsService.searchSkillByName("fireball");

        assertTrue(result.isPresent());
        assertEquals("Fireball", result.get().getName());
    }

    @Test
    void getSkillsByCharId_ShouldReturnList() {
        when(skillsRepository.findSkillsByCharacters_Id(10L)).thenReturn(List.of(skill));

        List<SkillsDTO> result = skillsService.getSkillsByCharId(10L);

        assertEquals(1, result.size());
        assertEquals("Fireball", result.getFirst().getName());
    }
}
