package com.reactive.patterns.ReactivePatterns.sec03.service;

import com.reactive.patterns.ReactivePatterns.sec03.client.ShippingClient;
import com.reactive.patterns.ReactivePatterns.sec03.dto.OrchestrationRequestContext;
import com.reactive.patterns.ReactivePatterns.sec03.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;
@Service
public class ShippingOrchestrator extends Orchestrator {
    @Autowired
    private ShippingClient client;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
      return client.schedule(context.getShippingRequest())
               .doOnNext(context::setShippingResponse)
               .thenReturn(context);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> context.getShippingResponse().getStatus().equals(Status.SUCCESS);
    }

    @Override
    public void cancel(OrchestrationRequestContext context) {
        Mono.just(context)
                .filter(isSuccess())
                .flatMap(ctx -> client.cancel(ctx.getShippingRequest()))
                .subscribe();
    }
}
