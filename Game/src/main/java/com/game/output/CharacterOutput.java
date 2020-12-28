package com.game.output;

import com.game.enums.RaceEnum;
import lombok.Builder;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@Setter
public class CharacterOutput {
    private BigInteger id;
    private String characterName;
    private String characterGender;
    private RaceEnum race;
    private BigInteger hp;
    private BigInteger attack;
    private BigInteger defense;
    private LocalDateTime dateOfCreation;
    private ItemOutput itemOutput;
}
