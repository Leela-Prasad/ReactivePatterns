package com.reactive.patterns.ReactivePatterns.sec05.controller;

import com.reactive.patterns.ReactivePatterns.sec05.dto.ReservationItemRequest;
import com.reactive.patterns.ReactivePatterns.sec05.dto.ReservationResponse;
import com.reactive.patterns.ReactivePatterns.sec05.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService service;

    @PostMapping("sec05/reserve")
    public Mono<ReservationResponse> reserve(@RequestBody Flux<ReservationItemRequest> flux) {
        return service.service(flux);
    }
}
