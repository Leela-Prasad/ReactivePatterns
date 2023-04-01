package com.reactive.patterns.ReactivePatterns.sec01.dto;

import lombok.Data;

@Data
public class ReviewResponse {
    private int id;
    private int rating;
    private String comment;
    private String user;
}
