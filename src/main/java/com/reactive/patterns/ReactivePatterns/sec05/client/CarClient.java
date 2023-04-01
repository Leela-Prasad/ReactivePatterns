package com.reactive.patterns.ReactivePatterns.sec05.client;

import com.reactive.patterns.ReactivePatterns.sec05.dto.CarReservationRequest;
import com.reactive.patterns.ReactivePatterns.sec05.dto.CarReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarClient {

    @Value("${sec05.car.service}")
    private String baseUrl;

    public Flux<CarReservationResponse> reserve(Flux<CarReservationRequest> flux) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

       return webClient.post()
                .body(flux, CarReservationRequest.class)
                .retrieve()
                .bodyToFlux(CarReservationResponse.class)
                .onErrorResume(ex -> Mono.empty());
    }
}
