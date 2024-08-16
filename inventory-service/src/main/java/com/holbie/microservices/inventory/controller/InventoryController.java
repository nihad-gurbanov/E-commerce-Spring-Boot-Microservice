package com.holbie.microservices.inventory.controller;

import com.holbie.microservices.inventory.model.Inventory;
import com.holbie.microservices.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        return inventoryService.isInStock(skuCode, quantity);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> findAll() {
        return inventoryService.getAll();
    }

}
