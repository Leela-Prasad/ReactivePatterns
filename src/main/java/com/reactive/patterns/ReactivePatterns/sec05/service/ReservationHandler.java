package com.reactive.patterns.ReactivePatterns.sec05.service;

import com.reactive.patterns.ReactivePatterns.sec05.dto.ReservationItemRequest;
import com.reactive.patterns.ReactivePatterns.sec05.dto.ReservationItemResponse;
import com.reactive.patterns.ReactivePatterns.sec05.dto.ReservationType;
import reactor.core.publisher.Flux;

public abstract class ReservationHandler {

    public abstract ReservationType getType();
    public abstract Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux);
}
