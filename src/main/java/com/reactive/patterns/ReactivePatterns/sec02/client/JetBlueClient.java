package com.reactive.patterns.ReactivePatterns.sec02.client;

import com.reactive.patterns.ReactivePatterns.sec02.dto.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
@Service
public class JetBlueClient {

    @Value("${sec02.jetblue.service}")
    private String baseUrl;

    public Flux<FlightResult> getFlights(String from, String  to) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.get()
                .uri("{from}/{to}", from, to)
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .doOnNext(fr -> normalize(fr, from, to))
                .onErrorResume(t -> Flux.empty());
    }

    private void normalize(FlightResult fr, String from, String to) {
        fr.setAirline("JETBLUE");
        fr.setFrom(from);
        fr.setTo(to);
    }

}
