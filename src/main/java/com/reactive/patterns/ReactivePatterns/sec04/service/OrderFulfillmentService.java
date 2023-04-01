package com.reactive.patterns.ReactivePatterns.sec04.service;

import com.reactive.patterns.ReactivePatterns.sec04.client.PaymentClient;
import com.reactive.patterns.ReactivePatterns.sec04.client.ProductClient;
import com.reactive.patterns.ReactivePatterns.sec04.dto.OrchestrationRequestContext;
import com.reactive.patterns.ReactivePatterns.sec04.dto.Status;
import com.reactive.patterns.ReactivePatterns.sec04.util.OrchestrationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderFulfillmentService {

    @Autowired ProductClient client;
    @Autowired PaymentOrchestrator paymentOrchestrator;
    @Autowired InventoryOrchestrator inventoryOrchestrator;
    @Autowired ShippingOrchestrator shippingOrchestrator;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext context) {
         return getProduct(context)
                .doOnNext(OrchestrationUtil::buildPaymentRequest)
                .flatMap(paymentOrchestrator::create)
                 .doOnNext(OrchestrationUtil::buildInventoryRequest)
                 .flatMap(inventoryOrchestrator::create)
                 .doOnNext(OrchestrationUtil::buildShippingRequest)
                 .flatMap(shippingOrchestrator::create)
                 .doOnNext(ctx -> ctx.setStatus(Status.SUCCESS))
                 .doOnError(ex -> context.setStatus(Status.FAILED))
                 .onErrorReturn(context);

    }

    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext context) {
        return client.getProduct(context.getOrderRequest().getProductId())
                .map(pr -> pr.getPrice())
                .doOnNext(context::setProductPrice)
                .map(price -> context);
    }

}
