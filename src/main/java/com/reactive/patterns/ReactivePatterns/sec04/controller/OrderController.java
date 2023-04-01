package com.reactive.patterns.ReactivePatterns.sec04.controller;

import com.reactive.patterns.ReactivePatterns.sec04.dto.OrderRequest;
import com.reactive.patterns.ReactivePatterns.sec04.dto.OrderResponse;
import com.reactive.patterns.ReactivePatterns.sec04.service.OrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class OrderController {

    @Autowired
    private OrchestrationService service;

    @PostMapping("/sec04/order")
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Mono<OrderRequest> orderRequestMono) {
       return service.placeOrder(orderRequestMono)
               .map(ResponseEntity::ok)
               .defaultIfEmpty(ResponseEntity.notFound().build());

    }

}
