package com.reactive.patterns.ReactivePatterns.sec01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "create")
public class ProductAggregate {
    private int id;
    private String category;
    private String description;
    private Price price;
    private List<ReviewResponse> reviews;
}
