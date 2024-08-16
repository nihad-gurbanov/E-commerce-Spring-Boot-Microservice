package com.holbie.microservices.inventory.service;

import com.holbie.microservices.inventory.model.Inventory;
import com.holbie.microservices.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }

    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

}
