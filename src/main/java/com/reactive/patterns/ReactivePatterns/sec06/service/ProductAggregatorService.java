package com.reactive.patterns.ReactivePatterns.sec06.service;

import com.reactive.patterns.ReactivePatterns.sec06.client.ProductClient;
import com.reactive.patterns.ReactivePatterns.sec06.client.ReviewClient;
import com.reactive.patterns.ReactivePatterns.sec06.dto.ProductAggregate;
import com.reactive.patterns.ReactivePatterns.sec06.dto.ProductResponse;
import com.reactive.patterns.ReactivePatterns.sec06.dto.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductAggregatorService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(int id) {
        return Mono.zip(productClient.getProduct(id),
                reviewClient.getReview(id))
                .map(t -> toProductAggregateDto(t.getT1(), t.getT2()));
    }

    private ProductAggregate toProductAggregateDto(ProductResponse product,
                                                   List<ReviewResponse> reviews) {

        return ProductAggregate.create(product.getId(),
                                product.getCategory(),
                                product.getDescription(),
                                reviews);
    }
}
