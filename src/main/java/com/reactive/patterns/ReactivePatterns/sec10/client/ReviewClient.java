package com.reactive.patterns.ReactivePatterns.sec10.client;

import com.reactive.patterns.ReactivePatterns.sec10.dto.ReviewResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {

    @Value("${sec10.review.service}")
    private String baseUrl;

    @RateLimiter(name = "review-service", fallbackMethod = "getReviewFallback")
    public Mono<List<ReviewResponse>> getReview(int id) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.get()
                .uri("{id}", id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.empty())
                .bodyToFlux(ReviewResponse.class)
                .collectList();
    }

    public Mono<List<ReviewResponse>> getReviewFallback(int id, Throwable t) {
        return Mono.just(Collections.emptyList());
    }

}
