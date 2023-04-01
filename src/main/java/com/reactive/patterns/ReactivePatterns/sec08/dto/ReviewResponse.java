package com.reactive.patterns.ReactivePatterns.sec08.dto;

import lombok.Data;

@Data
public class ReviewResponse {
    private int id;
    private int rating;
    private String comment;
    private String user;
}
