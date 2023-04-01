package com.reactive.patterns.ReactivePatterns.sec06.client;

import com.reactive.patterns.ReactivePatterns.sec06.dto.ReviewResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {

    @Value("${sec06.review.service}")
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
                .timeout(Duration.ofMillis(500))
                .onErrorReturn(Collections.emptyList());
    }
}
