package com.reactive.patterns.ReactivePatterns.sec03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PaymentResponse {
    private int balance;
    private String name;
    private Status status;
    private int userId;
}
