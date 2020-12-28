package com.game.model;

import com.game.enums.RaceEnum;
import lombok.*;

import java.time.LocalDateTime;
import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterEntity {
    @Id
    @GeneratedValue
    private BigInteger id;
    private String characterName;
    private String characterGender;
    @Enumerated (EnumType.STRING)
    private RaceEnum race;
    private BigInteger hp;
    private BigInteger attack;
    private BigInteger defense;
    private LocalDateTime dateOfCreation;
}
