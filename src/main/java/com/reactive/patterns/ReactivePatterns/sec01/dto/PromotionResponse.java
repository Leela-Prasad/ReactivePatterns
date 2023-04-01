package com.reactive.patterns.ReactivePatterns.sec01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponse {
    private int id;
    private String type;
    private float discount;
    private LocalDate endDate;
}
