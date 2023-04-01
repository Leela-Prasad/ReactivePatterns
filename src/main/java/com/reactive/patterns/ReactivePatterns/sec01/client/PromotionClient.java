package com.reactive.patterns.ReactivePatterns.sec01.client;

import com.reactive.patterns.ReactivePatterns.sec01.dto.PromotionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class PromotionClient {

    @Value("${sec01.promotion.service}")
    private String baseUrl;

    public Mono<PromotionResponse> getPromotion(int id) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(PromotionResponse.class)
                .onErrorReturn(new PromotionResponse(0, "no promotion", 0.0f, LocalDate.now()));
    }
}
