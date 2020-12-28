package com.game.repository;

import com.game.model.ItemEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

public interface ItemRepository extends CrudRepository<ItemEntity, BigInteger> {
    List<ItemEntity> findByName(String name);
}
