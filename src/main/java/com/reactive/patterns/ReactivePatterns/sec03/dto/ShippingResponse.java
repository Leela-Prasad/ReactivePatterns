package com.reactive.patterns.ReactivePatterns.sec03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ShippingResponse {
    private UUID orderId;
    private int quantity;
    private Status status;
    private String expectedDelivery;
    private Address address;
}
