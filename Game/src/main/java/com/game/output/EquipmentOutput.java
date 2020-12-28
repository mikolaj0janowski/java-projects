package com.game.output;

import com.game.model.EquipmentEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class EquipmentOutput {
    ItemOutput itemOutput;

    List<CharacterOutput> characterOutputList;

    List<EquipmentEntity> equipmentEntityList;
}
