package com.reactive.patterns.ReactivePatterns.sec05.service;

import com.reactive.patterns.ReactivePatterns.sec05.dto.ReservationItemRequest;
import com.reactive.patterns.ReactivePatterns.sec05.dto.ReservationItemResponse;
import com.reactive.patterns.ReactivePatterns.sec05.dto.ReservationResponse;
import com.reactive.patterns.ReactivePatterns.sec05.dto.ReservationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReservationService {

//    @Autowired
//    private List<ReservationHandler> handlers;

    private Map<ReservationType, ReservationHandler> map;

    public ReservationService(@Autowired List<ReservationHandler> handlers) {
       map = handlers.stream().collect(Collectors.toMap(
                ReservationHandler::getType,
                Function.identity()
        ));
    }

    public Mono<ReservationResponse> service(Flux<ReservationItemRequest> flux) {
        Flux<GroupedFlux<ReservationType, ReservationItemRequest>> groupedFlux =
                flux.groupBy(ReservationItemRequest::getType);

        return groupedFlux.flatMap(this::aggregator)
                .collectList()
                .map(this::toResponse);
    }

    private Flux<ReservationItemResponse> aggregator(GroupedFlux<ReservationType, ReservationItemRequest> groupedFlux) {
        ReservationType key = groupedFlux.key();
        ReservationHandler handler = map.get(key);

        Flux<ReservationItemResponse> response = handler.reserve(groupedFlux);
        return response;
    }

    private ReservationResponse toResponse(List<ReservationItemResponse> list) {
        return ReservationResponse.create(
               UUID.randomUUID(),
               list.stream().mapToInt(ReservationItemResponse::getPrice).sum(),
                list
        );
    }
}
