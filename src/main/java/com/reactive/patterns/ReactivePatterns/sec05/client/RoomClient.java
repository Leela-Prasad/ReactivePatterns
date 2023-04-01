package com.reactive.patterns.ReactivePatterns.sec05.client;

import com.reactive.patterns.ReactivePatterns.sec05.dto.CarReservationRequest;
import com.reactive.patterns.ReactivePatterns.sec05.dto.CarReservationResponse;
import com.reactive.patterns.ReactivePatterns.sec05.dto.RoomReservationRequest;
import com.reactive.patterns.ReactivePatterns.sec05.dto.RoomReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomClient {
    @Value("${sec05.room.service}")
    private String baseUrl;

    public Flux<RoomReservationResponse> reserve(Flux<RoomReservationRequest> flux) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.post()
                .body(flux, RoomReservationRequest.class)
                .retrieve()
                .bodyToFlux(RoomReservationResponse.class)
                .onErrorResume(ex -> Mono.empty());
    }
}
