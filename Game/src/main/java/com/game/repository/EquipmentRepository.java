package com.game.repository;

import com.game.model.EquipmentEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

public interface EquipmentRepository extends CrudRepository <EquipmentEntity, BigInteger>{
    List<EquipmentEntity> findByOwnerID(BigInteger ownerID);
    List<EquipmentEntity> findByItemID(BigInteger itemID);
}
