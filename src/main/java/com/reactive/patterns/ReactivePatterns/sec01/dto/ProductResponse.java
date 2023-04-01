package com.reactive.patterns.ReactivePatterns.sec01.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private int id;
    private String description;
    private String category;
    private float price;
}
