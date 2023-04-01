package com.reactive.patterns.ReactivePatterns.sec04.service;

import com.reactive.patterns.ReactivePatterns.sec04.client.PaymentClient;
import com.reactive.patterns.ReactivePatterns.sec04.dto.OrchestrationRequestContext;
import com.reactive.patterns.ReactivePatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
@Service
public class PaymentOrchestrator extends Orchestrator {

    @Autowired
    private PaymentClient client;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return client.deduct(context.getPaymentRequest())
                .doOnNext(context::setPaymentResponse)
                .thenReturn(context)
                .handle(statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> Objects.nonNull(context.getPaymentResponse()) &&
                context.getPaymentResponse().getStatus().equals(Status.SUCCESS);
    }

    @Override
    public void cancel(OrchestrationRequestContext context) {
        Mono.just(context)
                .filter(isSuccess())
                .flatMap(ctx -> client.refund(ctx.getPaymentRequest()))
                .subscribe();
    }
}
