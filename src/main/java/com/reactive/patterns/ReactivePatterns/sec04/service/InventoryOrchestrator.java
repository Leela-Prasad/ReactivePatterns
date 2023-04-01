package com.reactive.patterns.ReactivePatterns.sec04.service;

import com.reactive.patterns.ReactivePatterns.sec04.client.InventoryClient;
import com.reactive.patterns.ReactivePatterns.sec04.dto.OrchestrationRequestContext;
import com.reactive.patterns.ReactivePatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;

@Service
public class InventoryOrchestrator extends Orchestrator {

    @Autowired
    private InventoryClient client;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return client.deduct(context.getInventoryRequest())
                .doOnNext(context::setInventoryResponse)
                .thenReturn(context)
                .handle(statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> Objects.nonNull(context.getInventoryResponse()) &&
                context.getInventoryResponse().getStatus().equals(Status.SUCCESS);
    }

    @Override
    public void cancel(OrchestrationRequestContext context) {
        Mono.just(context)
                .filter(isSuccess())
                .flatMap(ctx -> client.restore(ctx.getInventoryRequest()))
                .subscribe();
    }
}
