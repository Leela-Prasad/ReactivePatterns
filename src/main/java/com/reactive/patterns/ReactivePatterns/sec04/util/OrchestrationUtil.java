package com.reactive.patterns.ReactivePatterns.sec04.util;

import com.reactive.patterns.ReactivePatterns.sec04.dto.*;

public class OrchestrationUtil {

    public static void buildPaymentRequest(OrchestrationRequestContext ctx) {
        OrderRequest orderRequest = ctx.getOrderRequest();
        PaymentRequest paymentRequest = PaymentRequest.create(
                (int) (orderRequest.getQuantity() * ctx.getProductPrice()),
                orderRequest.getUserId(),
                ctx.getOrderId()
        );
        ctx.setPaymentRequest(paymentRequest);
    }

    public static void buildInventoryRequest(OrchestrationRequestContext ctx) {
        OrderRequest orderRequest = ctx.getOrderRequest();
        InventoryRequest inventoryRequest = InventoryRequest.create(
                ctx.getPaymentResponse().getPaymentId(),
                orderRequest.getProductId(),
                orderRequest.getQuantity()
        );
        ctx.setInventoryRequest(inventoryRequest);
    }

    public static void buildShippingRequest(OrchestrationRequestContext ctx) {
        OrderRequest orderRequest = ctx.getOrderRequest();
        ShippingRequest shippingRequest = ShippingRequest.create(
                orderRequest.getUserId(),
                orderRequest.getQuantity(),
                ctx.getInventoryResponse().getInventoryId()
        );
        ctx.setShippingRequest(shippingRequest);
    }
}
