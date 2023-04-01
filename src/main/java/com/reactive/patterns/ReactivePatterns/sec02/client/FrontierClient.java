package com.reactive.patterns.ReactivePatterns.sec02.client;

import com.reactive.patterns.ReactivePatterns.sec02.dto.FlightResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
@Service
public class FrontierClient {

    @Value("${sec02.frontier.service}")
    private String baseUrl;

    public Flux<FlightResult> getFlights(String from, String  to) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.post()
                .bodyValue(FlightRequest.create(from, to))
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(t -> Flux.empty());
    }

    @Data
    @AllArgsConstructor(staticName = "create")
    public static class FlightRequest {
        private String from;
        private String to;
    }
}
