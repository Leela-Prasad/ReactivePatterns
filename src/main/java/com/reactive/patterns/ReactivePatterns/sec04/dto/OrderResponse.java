package com.reactive.patterns.ReactivePatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class OrderResponse {
    private int userId;
    private int productId;
    private UUID orderId;
    private Status status;
    private Address shippingAddress;
    private String expectedDelivery;
}
