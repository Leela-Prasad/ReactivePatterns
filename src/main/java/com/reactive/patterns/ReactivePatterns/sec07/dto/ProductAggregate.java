package com.reactive.patterns.ReactivePatterns.sec07.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "create")
public class ProductAggregate {
    private int id;
    private String category;
    private String description;
    private List<ReviewResponse> reviews;
}
