package com.reactive.patterns.ReactivePatterns.sec03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class InventoryResponse {
    private int productId;
    private int quantity;
    private int remainingQuantity;
    private Status status;
}
