package com.reactive.patterns.ReactivePatterns.sec07.client;

import com.reactive.patterns.ReactivePatterns.sec07.dto.ReviewResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {

    @Value("${sec07.review.service}")
    private String baseUrl;

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
//                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .timeout(Duration.ofMillis(400))
                .onErrorReturn(Collections.emptyList());
    }
}
