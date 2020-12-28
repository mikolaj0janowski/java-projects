package com.game.repository;

import com.game.model.CharacterEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

public interface CharacterRepository extends CrudRepository<CharacterEntity, BigInteger> {
    List<CharacterEntity> findByAttackAndDefense(BigInteger attack, BigInteger defense);
}
