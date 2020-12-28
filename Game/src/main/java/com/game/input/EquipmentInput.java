package com.game.input;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Builder
@Getter
@Setter
public class EquipmentInput {
    private BigInteger id;
    private BigInteger ownerID;
    private BigInteger itemID;
}
