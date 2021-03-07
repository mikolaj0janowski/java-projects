package com.game.service;

import com.game.input.EquipmentInput;
import com.game.model.CharacterEntity;
import com.game.model.EquipmentEntity;
import com.game.model.ItemEntity;
import com.game.output.CharacterOutput;
import com.game.output.EquipmentOutput;
import com.game.output.ItemOutput;
import com.game.repository.CharacterRepository;
import com.game.repository.EquipmentRepository;
import com.game.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CharacterRepository characterRepository;

    public EquipmentOutput getEquipmentById(BigInteger id) {
        EquipmentEntity equipmentEntity = equipmentRepository.findById(id)
                .orElseGet(EquipmentEntity::new);
        return equipmentEntityToEquipmentOutput(equipmentEntity);
    }

    public void delete(BigInteger id) {
        equipmentRepository.deleteById(id);
    }

    public EquipmentEntity saveNewEquipment(EquipmentInput input) {
        if (validateInput(input)) {
            EquipmentEntity equipmentEntity = prepareEquipmentEntity(input);
            return equipmentRepository.save(equipmentEntity);
        } else {
            throw new IllegalArgumentException("wrong input");
        }
    }

    public void updateEntity(EquipmentInput equipmentInput, EquipmentEntity entityToUpdate) {
        Optional.ofNullable(equipmentInput)
                .map(EquipmentInput::getItemID)
                .ifPresent(entityToUpdate::setItemID);
        Optional.ofNullable(equipmentInput)
                .map(EquipmentInput::getOwnerID)
                .ifPresent(entityToUpdate::setOwnerID);
        equipmentRepository.save(entityToUpdate);
    }

    public EquipmentOutput saveOrUpdate(BigInteger id, EquipmentInput equipmentInput) {
        EquipmentEntity entityToUpdate = equipmentRepository.findById(id)
                .orElse(null);
        if (entityToUpdate != null) {
            updateEntity(equipmentInput, entityToUpdate);
        } else {
            saveNewEquipment(equipmentInput);
        }
        return EquipmentOutput.builder()
                .equipmentEntityList(Collections.singletonList(entityToUpdate))
                .build();
    }

    public EquipmentOutput findAllItemsByOwnerID(BigInteger ownerID) {
        List<EquipmentEntity> equipmentEntity = equipmentRepository.findByOwnerID(ownerID);
        List<ItemEntity> itemEntityList = equipmentEntity.stream()
                .map(item -> itemRepository.findById(item.getItemID()).get())
                .collect(Collectors.toList());
        return EquipmentOutput.builder()
                .itemOutput(ItemOutput.builder()
                        .itemEntityList(itemEntityList)
                        .build())
                .build();
    }

    public EquipmentOutput findAllOwnersByItemID(BigInteger itemID) {
        List<EquipmentEntity> equipmentEntity = equipmentRepository.findByItemID(itemID);
        List<CharacterEntity> characterEntities = equipmentEntity.stream()
                .map(character -> characterRepository.findById(character.getOwnerID()).get())
                .collect(Collectors.toList());
        return EquipmentOutput.builder()
                .characterOutputList(mapEntityToCharacterOutput(characterEntities))
                .build();
    }

    private List<CharacterOutput> mapEntityToCharacterOutput(List<CharacterEntity> characterEntityList){
        List<CharacterOutput> characterOutputList = new ArrayList<>();
        characterEntityList.forEach(characterEntity->{
            EquipmentOutput allItemsByOwnerID = findAllItemsByOwnerID(characterEntity.getId());
            CharacterOutput characterOutput = CharacterOutput.builder()
                    .attack(characterEntity.getAttack())
                    .characterGender(characterEntity.getCharacterGender())
                    .characterName(characterEntity.getCharacterName())
                    .dateOfCreation(characterEntity.getDateOfCreation())
                    .defense(characterEntity.getDefense())
                    .hp(characterEntity.getHp())
                    .id(characterEntity.getId())
                    .race(characterEntity.getRace())
                    .itemOutput(allItemsByOwnerID.getItemOutput())
                    .build();
            characterOutputList.add(characterOutput);
        });
        return characterOutputList;
    }

    private EquipmentOutput equipmentEntityToEquipmentOutput(EquipmentEntity equipmentEntity) {
        return EquipmentOutput.builder()
                .equipmentEntityList(Collections.singletonList(equipmentEntity))
                .build();
    }

    private EquipmentEntity prepareEquipmentEntity(EquipmentInput input) {
        return EquipmentEntity.builder()
                .itemID(input.getItemID())
                .ownerID(input.getOwnerID())
                .build();
    }

    private boolean validateInput(EquipmentInput input) {
        if (input.getOwnerID() == null || input.getItemID() == null) {
            return false;
        }
        ItemEntity itemEntity = itemRepository.findById(input.getItemID())
                .orElse(null);
        CharacterEntity characterEntity = characterRepository.findById(input.getOwnerID())
                .orElse(null);
        return !(itemEntity == null || characterEntity == null);
    }
}
