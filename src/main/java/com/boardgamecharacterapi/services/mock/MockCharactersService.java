package com.boardgamecharacterapi.services.mock;

import com.boardgamecharacterapi.models.Characters;
import com.boardgamecharacterapi.models.Games;
import com.boardgamecharacterapi.models.Skills;
import com.boardgamecharacterapi.models.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MockCharactersService {

    public enum CharacterNameMode {
        NORMAL,
        WITH_ENUM_PREFIX
    }

    public List<Characters> getAllCharactersWithAll() {
        return createCharacters(CharacterNameMode.WITH_ENUM_PREFIX);
    }

    public List<Characters> getAllCharacters() {
        return createCharacters(CharacterNameMode.NORMAL);
    }

    private List<Characters> createCharacters(CharacterNameMode mode) {
        Games game = new Games();
        game.setId(1L);
        game.setTitle("Citadels");

        Skills skill = new Skills();
        skill.setId(1L);
        skill.setName("Stealth");
        skill.setDescription("can be invisible for one turn");

        Skills skill1 = new Skills();
        skill1.setId(2L);
        skill1.setName("warrior");
        skill1.setDescription("can fight twice");

        Set<Skills> skillsAss = new HashSet<>();
        skillsAss.add(skill);

        Set<Skills> skillsWar = new HashSet<>();
        skillsWar.add(skill1);

        Characters character1 = new Characters();
        character1.setId(1L);
        character1.setName(formatName("Assassins", mode));
        character1.setDescription("Description assassins");
        character1.setSkills(skillsAss);

        Characters character2 = new Characters();
        character2.setId(2L);
        character2.setName(formatName("Warrior", mode));
        character2.setDescription("Description Warrior");
        character2.setSkills(skillsWar);

        Type type1 = new Type();
        type1.setId(1L);
        type1.setName("Assassins");
        character1.setType(type1);

        Type type2 = new Type();
        type2.setId(2L);
        type2.setName("Warrior");
        character2.setType(type2);

        Set<Characters> characters = new HashSet<>();
        characters.add(character1);
        characters.add(character2);

        game.setCharacters(characters);
        return new ArrayList<>(characters);
    }

    private String formatName(String baseName, CharacterNameMode mode) {
        if (mode == CharacterNameMode.WITH_ENUM_PREFIX) {
            return "EnumTypeCharacter." + baseName;
        }
        return baseName;
    }
}