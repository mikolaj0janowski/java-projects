package com.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentEntity {
    @Id
    @GeneratedValue
    private BigInteger id;
    private BigInteger ownerID;
    private BigInteger itemID;
}
