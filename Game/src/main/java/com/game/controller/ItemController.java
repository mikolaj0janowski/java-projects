package com.game.controller;

import com.game.input.ItemInput;
import com.game.model.ItemEntity;
import com.game.service.ItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/")
@Api(value = "ItemController", description = "It controls items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("allitems")
    private List<ItemEntity> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("items/{id}")
    private ItemEntity getItem(@PathVariable("id") BigInteger id) {
        return itemService.getItemById(id);
    }

    @DeleteMapping("items/{id}")
    private void deleteItem(@PathVariable("id") BigInteger id) {
        itemService.delete(id);
    }

    @PostMapping("items")
    private void saveItem(
            @RequestParam(name = "name") final String name,
            @RequestParam(name = "plusHP") final BigInteger addHP,
            @RequestParam(name = "plusAttack") final BigInteger addAttack,
            @RequestParam(name = "plusDefense") final BigInteger addDefense
    ) {
        itemService.saveNewItem(ItemInput.builder()
                .name(name)
                .addHP(addHP)
                .addAttack(addAttack)
                .addDefense(addDefense)
                .build());
    }

    @PutMapping("items/{id}")
    private ItemEntity updateItem(@PathVariable("id") BigInteger id, @RequestBody ItemInput itemInput) {
        return itemService.saveOrUpdate(id, itemInput);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class NotFound extends RuntimeException {
    }

    @GetMapping("findByName")
    private List<ItemEntity> findByName(String name) {
        return itemService.findByName(name);
    }
}

