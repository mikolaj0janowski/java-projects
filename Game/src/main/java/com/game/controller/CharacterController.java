package com.game.controller;

import com.game.input.CharacterInput;
import com.game.enums.RaceEnum;
import com.game.model.CharacterEntity;
import com.game.service.CharacterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/")
@Api(value = "CharacterController", description = "It controls characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping("allcharacters")
    private List<CharacterEntity> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    @GetMapping("characters/{id}")
    private CharacterEntity getCharacter(@PathVariable("id") BigInteger id) {
        return characterService.getCharacterById(id);
    }

    @DeleteMapping("characters/{id}")
    private void deleteCharacter(@PathVariable("id") BigInteger id) {
        characterService.delete(id);
    }

    @PostMapping("characters")
    private CharacterEntity saveCharacter(
            @RequestParam(name = "hp") final BigInteger hp,
            @RequestParam(name = "attack") final BigInteger attack,
            @RequestParam(name = "defense") final BigInteger defense,
            @RequestParam(name = "name") final String name,
            @RequestParam(name = "gender") final String gender,
            @RequestParam(name = "race") final RaceEnum race
    ) {
        return characterService.saveNewCharacter(CharacterInput.builder()
                .attack(attack)
                .characterName(name)
                .characterGender(gender)
                .race(race)
                .defense(defense)
                .hp(hp)
                .dateOfCreation(LocalDateTime.now())
                .build());
    }

    @PutMapping("characters/{id}")
    private CharacterEntity updateCharacter(@PathVariable("id") BigInteger id, @RequestBody CharacterInput characterInput) {
        return characterService.saveOrUpdate(id, characterInput);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class NotFound extends RuntimeException {
    }

    @GetMapping("findByAttackAndDefense")
    private List<CharacterEntity> findByAttackAndDefense(BigInteger attack, BigInteger defense) {
        return characterService.findByAttackAndDefense(attack, defense);
    }
}
