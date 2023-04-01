package com.reactive.patterns.ReactivePatterns.sec05.service;

import com.reactive.patterns.ReactivePatterns.sec05.client.CarClient;
import com.reactive.patterns.ReactivePatterns.sec05.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
@Service
public class CarReservationHandler extends ReservationHandler {
    @Autowired
    private CarClient client;

    @Override
    public ReservationType getType() {
        return ReservationType.CAR;
    }

    @Override
    public Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux) {
       return flux.map(this::toCarRequest)
                .transform(client::reserve)
                .map(this::toResponse);
    }

    private CarReservationRequest toCarRequest(ReservationItemRequest request) {
        return CarReservationRequest.create(
                request.getCity(),
                request.getFrom(),
                request.getTo(),
                request.getCategory()
        );
    }

    private ReservationItemResponse toResponse(CarReservationResponse response) {
        return ReservationItemResponse.create(
                response.getReservationId(),
                getType(),
                response.getCategory(),
                response.getCity(),
                response.getPickup(),
                response.getDrop(),
                response.getPrice()
        );
    }
}
