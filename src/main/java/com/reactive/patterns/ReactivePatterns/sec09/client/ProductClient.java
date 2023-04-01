package com.reactive.patterns.ReactivePatterns.sec09.client;

import com.reactive.patterns.ReactivePatterns.sec09.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {
    @Value("${sec09.product.service}")
    private String baseUrl;

    public Mono<ProductResponse> getProduct(int id) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .onErrorResume(t -> Mono.empty());
    }
}
