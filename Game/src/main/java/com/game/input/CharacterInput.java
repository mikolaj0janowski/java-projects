package com.game.input;

import com.game.enums.RaceEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class CharacterInput {
    private String characterName;
    private String characterGender;
    private RaceEnum race;
    private LocalDateTime dateOfCreation;
    private BigInteger hp;
    private BigInteger attack;
    private BigInteger defense;
}
