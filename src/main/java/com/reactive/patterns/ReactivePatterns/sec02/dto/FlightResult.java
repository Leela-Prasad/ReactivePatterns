package com.reactive.patterns.ReactivePatterns.sec02.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightResult {
    private String airline;
    private String from;
    private String  to;
    private Double price;
    private LocalDate date;
}
