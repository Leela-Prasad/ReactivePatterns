package com.reactive.patterns.ReactivePatterns.sec01.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Price {
    private float listPrice;
    private float discount;
    private float discountPrice;
    private float amountSaved;
    private LocalDate endDate;
}
