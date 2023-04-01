package com.reactive.patterns.ReactivePatterns.sec03.util;

import com.reactive.patterns.ReactivePatterns.sec03.dto.*;

public class OrchestrationUtil {

    public static void buildRequestContext(OrchestrationRequestContext ctx) {
        buildPaymentRequest(ctx);
        buildInventoryRequest(ctx);
        buildShippingRequest(ctx);
    }

    private static void buildPaymentRequest(OrchestrationRequestContext ctx) {
        OrderRequest orderRequest = ctx.getOrderRequest();
        PaymentRequest paymentRequest = PaymentRequest.create(
                (int) (orderRequest.getQuantity() * ctx.getProductPrice()),
                orderRequest.getUserId(),
                ctx.getOrderId()
        );
        ctx.setPaymentRequest(paymentRequest);
    }

    private static void buildInventoryRequest(OrchestrationRequestContext ctx) {
        OrderRequest orderRequest = ctx.getOrderRequest();
        InventoryRequest inventoryRequest = InventoryRequest.create(
                ctx.getOrderId(),
                orderRequest.getProductId(),
                orderRequest.getQuantity()
        );
        ctx.setInventoryRequest(inventoryRequest);
    }

    private static void buildShippingRequest(OrchestrationRequestContext ctx) {
        OrderRequest orderRequest = ctx.getOrderRequest();
        ShippingRequest shippingRequest = ShippingRequest.create(
                orderRequest.getUserId(),
                orderRequest.getQuantity(),
                ctx.getOrderId()
        );
        ctx.setShippingRequest(shippingRequest);
    }
}
