package com.reactive.patterns.ReactivePatterns.sec03.controller;

import com.reactive.patterns.ReactivePatterns.sec03.dto.OrderRequest;
import com.reactive.patterns.ReactivePatterns.sec03.dto.OrderResponse;
import com.reactive.patterns.ReactivePatterns.sec03.service.OrchestrationService;
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

    @PostMapping("/sec03/order")
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Mono<OrderRequest> orderRequestMono) {
       return service.placeOrder(orderRequestMono)
               .map(ResponseEntity::ok)
               .defaultIfEmpty(ResponseEntity.notFound().build());

    }

}
