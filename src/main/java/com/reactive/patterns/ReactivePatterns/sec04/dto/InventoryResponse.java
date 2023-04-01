package com.reactive.patterns.ReactivePatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class InventoryResponse {
    private UUID inventoryId;
    private int productId;
    private int quantity;
    private int remainingQuantity;
    private Status status;
}
