package com.reactive.patterns.ReactivePatterns.sec10.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ProductAggregate {
    private int id;
    private String category;
    private String description;
    private List<ReviewResponse> reviews;
}
