package com.game.service;

import com.game.input.ItemInput;
import com.game.model.ItemEntity;
import com.game.repository.ItemRepository;
import com.game.utils.StringValidation;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {
    ItemRepository itemRepository;

    public List<ItemEntity> getAllItems() {
        return ImmutableList.copyOf(itemRepository.findAll());
    }

    public ItemEntity getItemById(BigInteger id) {
        BigInteger ourId = Optional.ofNullable(id)
                .orElse(BigInteger.ZERO);

        return itemRepository.findById(ourId)
                .orElse(null);
    }

    public ItemEntity saveOrUpdate(BigInteger id, ItemInput itemInput) {
        ItemEntity entityToUpdate = itemRepository.findById(id)
                .orElse(null);
        if(entityToUpdate != null) {
            updateItem(itemInput, entityToUpdate);
        }
        else{
            saveNewEntityForPut(itemInput);
        }
        return entityToUpdate;
    }

    public void delete(BigInteger id) {
        itemRepository.deleteById(id);
    }

    public ItemEntity saveNewItem(ItemInput build) {
        if (validateInput(build)) {
            ItemEntity itemEntity = prepareItemEntity(build);
            return itemRepository.save(itemEntity);
        } else {
            throw new IllegalArgumentException("wrong input");
        }

    }

    public List<ItemEntity> findByName(String name){
        String notNullName = Optional.ofNullable(name)
                .orElse("NoName");
        return itemRepository.findByName(notNullName);
    }

    private void saveNewEntityForPut(ItemInput itemInput) {
        saveNewItem(itemInput);
    }

    private void updateItem(ItemInput itemInput, ItemEntity entityToUpdate) {
        Optional.ofNullable(itemInput)
                .map(ItemInput::getAddHP)
                .ifPresent(entityToUpdate::setAddHP);
        Optional.ofNullable(itemInput)
                .map(ItemInput::getAddAttack)
                .ifPresent(entityToUpdate::setAddAttack);
        Optional.ofNullable(itemInput)
                .map(ItemInput::getAddDefense)
                .ifPresent(entityToUpdate::setAddDefense);
        Optional.ofNullable(itemInput)
                .map(ItemInput::getName)
                .ifPresent(entityToUpdate::setName);
        itemRepository.save(entityToUpdate);
    }

    private ItemEntity prepareItemEntity(ItemInput input) {
        return ItemEntity.builder()
                .name(Optional.ofNullable(input.getName())
                        .orElse("Nameless"))
                .addHP(Optional.ofNullable(input.getAddHP())
                        .orElse(BigInteger.valueOf(10)))
                .addAttack(Optional.ofNullable(input.getAddAttack())
                        .orElse(BigInteger.valueOf(2)))
                .addDefense(Optional.ofNullable(input.getAddDefense())
                        .orElse(BigInteger.valueOf(1)))
                .build();
    }

    private boolean validateInput(ItemInput input) {

        return StringValidation.validateString(input.getName());
    }
}
