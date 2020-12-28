package com.game.output;

import com.game.model.ItemEntity;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
public class ItemOutput {
    List<ItemEntity> itemEntityList;
}
