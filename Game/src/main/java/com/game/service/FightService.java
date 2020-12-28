package com.game.service;

import com.game.model.CharacterEntity;
import com.game.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FightService {

    @Autowired
    CharacterRepository repository;

    public List<CharacterEntity> fight(BigInteger defendingId, BigInteger attackerId) {
        CharacterEntity defendingCharacter = getCharacterById(defendingId);
        CharacterEntity attackingCharacter = getCharacterById(attackerId);

        validateCharacter(defendingCharacter);
        validateCharacter(attackingCharacter);
        validateFight(defendingCharacter.getHp(), attackingCharacter.getHp());

        performBattle(defendingCharacter, attackingCharacter);
        repository.save(defendingCharacter);
        repository.save(attackingCharacter);

        return Arrays.asList(defendingCharacter, attackingCharacter);
    }

    private void performBattle(CharacterEntity defending, CharacterEntity attacker) {
        defending.setHp(performHpLoss(defending.getHp(), defending.getDefense(), attacker.getAttack()));
        if (checkIfHpIsEqualOrBelowZero(defending.getHp())) {
            attacker.setHp(performHpLoss(attacker.getHp(), attacker.getDefense(), defending.getAttack()));
        }
    }

    private void validateCharacter(CharacterEntity character) {
        if (character.getAttack() == null
                || character.getCharacterName() == null
                || character.getDefense() == null
                || character.getHp() == null
        )
            throw new InvalidParameterException("Some null value is inside your character. Please contact administrator and start praying.");
    }

    private CharacterEntity getCharacterById(BigInteger id) {
        final Optional<CharacterEntity> retrievedCharacter = repository.findById(id);
        if (!retrievedCharacter.isPresent()) {
            throw new UnsupportedOperationException("Character of given id doesn't exist");
        }
        return retrievedCharacter.get();
    }

    private void validateFight(BigInteger firstHp, BigInteger secondHp) {
        if (checkIfHpIsEqualOrBelowZero(firstHp) || checkIfHpIsEqualOrBelowZero(secondHp)) {
            throw new UnsupportedOperationException("You are trying to fight with a dead character. Wanna grab some shovel?");
        }
    }

    private boolean checkIfHpIsEqualOrBelowZero(BigInteger hp) {
        return BigInteger.ZERO.compareTo(hp) > 0;
    }

    private BigInteger performHpLoss(BigInteger defendingHp, BigInteger defendingDefense, BigInteger attackerAttack) {
        if (checkIfDefenseIsGreaterThanAttack(defendingDefense, attackerAttack)) {
            return defendingHp;
        }
        return defendingHp.subtract(attackerAttack.subtract(defendingDefense)); // defending hp = defending hp - (attacker attack - dedenfing defense)
    }

    private boolean checkIfDefenseIsGreaterThanAttack(BigInteger defense, BigInteger attack) {
        return defense.compareTo(attack) > 0;
    }

    public List<CharacterEntity> randomFight(BigInteger id) {
        CharacterEntity attackingCharacter = getCharacterById(id);
        CharacterEntity defendingCharacter = getBalancedEnemy(attackingCharacter);

        validateCharacter(defendingCharacter);
        validateCharacter(attackingCharacter);
        validateFight(defendingCharacter.getHp(), attackingCharacter.getHp());

        performBattle(defendingCharacter, attackingCharacter);
        repository.save(defendingCharacter);
        repository.save(attackingCharacter);

        return Arrays.asList(defendingCharacter, attackingCharacter);
    }

    private CharacterEntity getBalancedEnemy(CharacterEntity character) { //FIXME change to something more smarter logic
        List<CharacterEntity> all = (List<CharacterEntity>) repository.findAll();
        final BigInteger defense = character.getDefense();
        final BigInteger attack = character.getAttack();
        final BigInteger hp = character.getHp();
        return all.stream()
                .filter(Objects::nonNull)
                .filter(item -> item.getDefense().compareTo(defense.subtract(BigInteger.TEN)) > 0
                        && item.getDefense().compareTo(defense.add(BigInteger.TEN)) < 1
                        && item.getAttack().compareTo(attack.subtract(BigInteger.TEN)) > 0
                        && item.getAttack().compareTo(attack.add(BigInteger.TEN)) < 1
                        && item.getHp().compareTo(hp.subtract(BigInteger.TEN)) > 0
                        && item.getHp().compareTo(hp.add(BigInteger.TEN)) < 1)
                .findFirst()
                .orElseGet(() -> all.stream().findFirst().get());
    }
}
