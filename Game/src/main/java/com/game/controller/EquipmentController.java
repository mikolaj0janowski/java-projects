package com.game.controller;

import com.game.input.EquipmentInput;
import com.game.output.EquipmentOutput;
import com.game.service.EquipmentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/")
@Api(value = "EquipmentController", description = "It controls character items")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("equipment/{id}")
    private EquipmentOutput getEquipment(@PathVariable("id") BigInteger id) {
        return equipmentService.getEquipmentById(id);
    }

    @DeleteMapping("equipment/{id}")
    private void deleteEquipment(@PathVariable("id") BigInteger id) {
        equipmentService.delete(id);
    }

    @PostMapping("equipment")
    private void saveEquipment(
            @RequestParam(name = "id") BigInteger id,
            @RequestParam(name = "ownerID") BigInteger ownerID,
            @RequestParam(name = "itemID") BigInteger itemID
    ) {
        equipmentService.saveNewEquipment(EquipmentInput.builder()
                .id(id)
                .ownerID(ownerID)
                .itemID(itemID)
                .build());
    }

    @PutMapping("equipment/{id}")
    private EquipmentOutput updateEquipment(@PathVariable("id") BigInteger id, @RequestBody EquipmentInput equipmentInput) {
        return equipmentService.saveOrUpdate(id, equipmentInput);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class NotFound extends RuntimeException {
    }

    @GetMapping("findByOwnerID")
    private EquipmentOutput findByOwnerID(BigInteger ownerID) {
        return equipmentService.findAllItemsByOwnerID(ownerID);
    }

    @GetMapping("findByItemID")
    private EquipmentOutput findByItemID(BigInteger itemID) {
        return equipmentService.findAllOwnersByItemID(itemID);
    }
}
