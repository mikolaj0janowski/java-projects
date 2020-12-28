package com.game.input;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Builder
@Getter
@Setter
public class ItemInput {
    private BigInteger addHP;
    private BigInteger addAttack;
    private BigInteger addDefense;
    private String name;
}
