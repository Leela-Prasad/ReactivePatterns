package com.reactive.patterns.ReactivePatterns.sec08.client;

import com.reactive.patterns.ReactivePatterns.sec08.dto.ReviewResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {

    @Value("${sec08.review.service}")
    private String baseUrl;

    @CircuitBreaker(name = "review-service", fallbackMethod = "getReviewFallback")
    public Mono<List<ReviewResponse>> getReview(int id) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.get()
                .uri("{id}", id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.empty())
                .bodyToFlux(ReviewResponse.class)
                .collectList()
                .retry(5)
                .timeout(Duration.ofMillis(400));
    }

    public Mono<List<ReviewResponse>> getReviewFallback(int id, Throwable t) {
        System.out.println("fallback review called: " + t.getMessage());
        return Mono.just(Collections.emptyList());
    }

}
