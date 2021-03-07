package com.game.service;

import com.game.input.CharacterInput;
import com.game.enums.RaceEnum;
import com.game.model.CharacterEntity;
import com.game.repository.CharacterRepository;
import com.game.utils.StringValidation;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    public List<CharacterEntity> getAllCharacters() {
        return ImmutableList.copyOf(characterRepository.findAll());
    }

    public CharacterEntity getCharacterById(BigInteger id) {
        BigInteger ourId = Optional.ofNullable(id)
                .orElse(BigInteger.ZERO);

        return characterRepository.findById(ourId)
                .orElse(null);
    }

    public CharacterEntity saveOrUpdate(BigInteger id, CharacterInput characterInput) { //FIXME change CharacterEntity to CharacterInput
        CharacterEntity entityToUpdate = characterRepository.findById(id)
                .orElse(null);
        if(entityToUpdate != null) {
            updateCharacter(characterInput, entityToUpdate);
        }
        else{
            saveNewEntityForPut(characterInput);
        }
        return entityToUpdate;
    }

    public void delete(BigInteger id) {
        characterRepository.deleteById(id);
    }

    public CharacterEntity saveNewCharacter(CharacterInput build) {
        if (validateInput(build)) {
            CharacterEntity characterEntity = prepareCharacterEntity(build);
            return characterRepository.save(characterEntity);
        } else {
            throw new IllegalArgumentException("wrong input");
        }

    }

    public List<CharacterEntity> findByAttackAndDefense(BigInteger attack, BigInteger defense){
        BigInteger notNullAttack = Optional.ofNullable(attack)
                .orElse(BigInteger.ZERO);
        BigInteger notNullDefense = Optional.ofNullable(defense)
                .orElse(BigInteger.ZERO);
        return characterRepository.findByAttackAndDefense(notNullAttack, notNullDefense);
    }

    private void saveNewEntityForPut(CharacterInput characterInput) {
        saveNewCharacter(characterInput);
    }

    private void updateCharacter(CharacterInput characterInput, CharacterEntity entityToUpdate) {
        Optional.ofNullable(characterInput)
                .map(CharacterInput::getHp)
                .ifPresent(entityToUpdate::setHp);
        Optional.ofNullable(characterInput)
                .map(CharacterInput::getAttack)
                .ifPresent(entityToUpdate::setAttack);
        Optional.ofNullable(characterInput)
                .map(CharacterInput::getDefense)
                .ifPresent(entityToUpdate::setDefense);
        Optional.ofNullable(characterInput)
                .map(CharacterInput::getCharacterName)
                .ifPresent(entityToUpdate::setCharacterName);
        Optional.ofNullable(characterInput)
                .map(CharacterInput::getCharacterGender)
                .ifPresent(entityToUpdate::setCharacterGender);
        Optional.ofNullable(characterInput)
                .map(CharacterInput::getRace)
                .ifPresent(entityToUpdate::setRace);
        characterRepository.save(entityToUpdate);
    }

    private CharacterEntity prepareCharacterEntity(CharacterInput input) {
        return CharacterEntity.builder()
                .characterName(Optional.ofNullable(input.getCharacterName())
                        .orElse("Nameless"))
                .characterGender(Optional.ofNullable(input.getCharacterGender())
                        .orElse("Genderless"))
                .race(Optional.ofNullable(input.getRace())
                        .orElse(RaceEnum.HUMAN))
                .dateOfCreation(Optional.ofNullable(input.getDateOfCreation())
                        .orElse(LocalDateTime.now()))
                .hp(Optional.ofNullable(input.getHp())
                        .orElse(BigInteger.valueOf(100)))
                .attack(Optional.ofNullable(input.getAttack())
                        .orElse(BigInteger.valueOf(20)))
                .defense(Optional.ofNullable(input.getDefense())
                        .orElse(BigInteger.valueOf(10)))
                .build();
    }

    private boolean validateInput(CharacterInput input) {

        return StringValidation.validateString(input.getCharacterName())
                && StringValidation.validateString(input.getCharacterGender());
    }
}
