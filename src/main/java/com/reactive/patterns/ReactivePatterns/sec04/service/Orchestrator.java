package com.reactive.patterns.ReactivePatterns.sec04.service;

import com.reactive.patterns.ReactivePatterns.sec04.dto.OrchestrationRequestContext;
import com.reactive.patterns.ReactivePatterns.sec04.exception.OrderFulfillmentFailure;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class Orchestrator {

    public abstract Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context);

    public abstract Predicate<OrchestrationRequestContext> isSuccess();

    public abstract void cancel(OrchestrationRequestContext context);

    protected BiConsumer<OrchestrationRequestContext, SynchronousSink<OrchestrationRequestContext>> statusHandler() {
        return (ctx, sink) -> {
            if(isSuccess().test(ctx))
                sink.next(ctx);
            else
                sink.error(new OrderFulfillmentFailure());
        };
    }
}
