package com.reactive.patterns.ReactivePatterns.sec05.service;

import com.reactive.patterns.ReactivePatterns.sec05.client.CarClient;
import com.reactive.patterns.ReactivePatterns.sec05.client.RoomClient;
import com.reactive.patterns.ReactivePatterns.sec05.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class RoomReservationHandler extends ReservationHandler {
    @Autowired
    private RoomClient client;

    @Override
    public ReservationType getType() {
        return ReservationType.ROOM;
    }

    @Override
    public Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux) {
        return flux.map(this::toRoomRequest)
                .transform(client::reserve)
                .map(this::toResponse);
    }

    private RoomReservationRequest toRoomRequest(ReservationItemRequest request) {
        return RoomReservationRequest.create(
                request.getCity(),
                request.getFrom(),
                request.getTo(),
                request.getCategory()
        );
    }

    private ReservationItemResponse toResponse(RoomReservationResponse response) {
        return ReservationItemResponse.create(
                response.getReservationId(),
                getType(),
                response.getCategory(),
                response.getCity(),
                response.getCheckIn(),
                response.getCheckOut(),
                response.getPrice()
        );
    }
}
