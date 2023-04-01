package com.reactive.patterns.ReactivePatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PaymentResponse {
    private int balance;
    private String name;
    private Status status;
    private UUID paymentId;
}
