package com.reactive.patterns.ReactivePatterns.sec06.client;

import com.reactive.patterns.ReactivePatterns.sec06.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ProductClient {
    @Value("${sec06.product.service}")
    private String baseUrl;

    public Mono<ProductResponse> getProduct(int id) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(t -> Mono.empty());
    }
}
