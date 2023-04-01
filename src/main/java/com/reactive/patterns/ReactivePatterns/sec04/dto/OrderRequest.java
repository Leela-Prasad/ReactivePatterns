package com.reactive.patterns.ReactivePatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class OrderRequest {
    private int userId;
    private int productId;
    private int quantity;
}
