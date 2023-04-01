package com.reactive.patterns.ReactivePatterns.sec10.controller;

import com.reactive.patterns.ReactivePatterns.sec10.dto.ProductAggregate;
import com.reactive.patterns.ReactivePatterns.sec10.service.ProductAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ProductAggregateController {

    @Autowired
    private ProductAggregatorService productAggregatorService;

    @GetMapping(value = "sec10/product/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<ProductAggregate>> getProductAggregate(@PathVariable int id) {
        return productAggregatorService.aggregate(id)
                .map(response -> ResponseEntity.ok(response))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
