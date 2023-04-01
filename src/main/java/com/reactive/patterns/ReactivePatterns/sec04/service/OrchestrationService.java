package com.reactive.patterns.ReactivePatterns.sec04.service;

import com.reactive.patterns.ReactivePatterns.sec04.client.ProductClient;
import com.reactive.patterns.ReactivePatterns.sec04.dto.*;
import com.reactive.patterns.ReactivePatterns.sec04.util.DebugUtil;
import com.reactive.patterns.ReactivePatterns.sec04.util.OrchestrationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrchestrationService {

    @Autowired
    private ProductClient client;

    @Autowired
    private OrderFulfillmentService fulfillmentService;

    @Autowired
    private OrderCancellationService cancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> request) {
        return request
            .map(OrchestrationRequestContext::new)
            .flatMap(fulfillmentService::placeOrder)
            .doOnNext(this::doOrderPostProcessing)
                .doOnNext(DebugUtil::print)
                .map(this::toOrderResponse);
    }
    private void doOrderPostProcessing(OrchestrationRequestContext context) {
        if(Status.FAILED.equals(context.getStatus()))
            cancellationService.cancelOrder(context);
    }

    private OrderResponse toOrderResponse(OrchestrationRequestContext context) {
        boolean isSuccess = context.getStatus().equals(Status.SUCCESS);
        Address address = isSuccess? context.getShippingResponse().getAddress(): null;
        String expectedDelivery = isSuccess? context.getShippingResponse().getExpectedDelivery(): null;

        return OrderResponse.create(
                context.getOrderRequest().getUserId(),
                context.getOrderRequest().getProductId(),
                context.getOrderId(),
                context.getStatus(),
                address,
                expectedDelivery
        );
    }
}
