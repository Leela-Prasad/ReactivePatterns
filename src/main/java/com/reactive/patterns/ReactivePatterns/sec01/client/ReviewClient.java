package com.reactive.patterns.ReactivePatterns.sec01.client;

import com.reactive.patterns.ReactivePatterns.sec01.dto.ReviewResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {

    @Value("${sec01.review.service}")
    private String baseUrl;

    public Mono<List<ReviewResponse>> getReview(int id) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.get()
                .uri("{id}", id)
                .retrieve()
                .bodyToFlux(ReviewResponse.class)
                .collectList()
                .onErrorReturn(Collections.emptyList());
    }
}
