package com.reactive.patterns.ReactivePatterns.sec03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PaymentRequest {
    private int amount;
    private int userId;
    private UUID orderId;
}
