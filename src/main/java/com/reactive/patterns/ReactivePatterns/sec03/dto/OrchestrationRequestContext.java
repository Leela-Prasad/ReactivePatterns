package com.reactive.patterns.ReactivePatterns.sec03.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrchestrationRequestContext {
    private final UUID orderId = UUID.randomUUID();
    private OrderRequest orderRequest;
    private float productPrice;
    private PaymentRequest paymentRequest;
    private PaymentResponse paymentResponse;
    private InventoryRequest inventoryRequest;
    private InventoryResponse inventoryResponse;
    private ShippingRequest shippingRequest;
    private ShippingResponse shippingResponse;
    private Status status;

    public OrchestrationRequestContext(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }
}
