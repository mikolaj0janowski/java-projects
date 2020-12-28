package com.game.controller;

import com.game.model.CharacterEntity;
import com.game.service.FightService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/")
@Api(value="FightController", description="It controls fights")
public class FightController {

    @Autowired
    FightService fightService;

    @GetMapping("fight")
    private List<CharacterEntity> twoCharactersFight(
            @RequestParam(name = "firstId") final BigInteger firstId,
            @RequestParam(name = "secondId") final BigInteger secondId
    ) {
        return fightService.fight(firstId, secondId);
    }

    @GetMapping("randomfight")
    private List<CharacterEntity> randomCharactersFight(
            @RequestParam(name = "id") final BigInteger id
    ) {
        return fightService.randomFight(id);
    }
}
