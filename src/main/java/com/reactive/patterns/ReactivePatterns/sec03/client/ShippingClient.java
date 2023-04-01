package com.reactive.patterns.ReactivePatterns.sec03.client;

import com.reactive.patterns.ReactivePatterns.sec03.dto.InventoryResponse;
import com.reactive.patterns.ReactivePatterns.sec03.dto.ShippingRequest;
import com.reactive.patterns.ReactivePatterns.sec03.dto.ShippingResponse;
import com.reactive.patterns.ReactivePatterns.sec03.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ShippingClient {
    @Value("${sec03.shipping.service}")
    private String baseUrl;

    private static final String SCHEDULE = "schedule";
    private static final String CANCEL = "cancel";

    public Mono<ShippingResponse> schedule(ShippingRequest request) {
        return callShippingService(SCHEDULE, request);
    }

    public Mono<ShippingResponse> cancel(ShippingRequest request) {
        return callShippingService(CANCEL, request);
    }

    private Mono<ShippingResponse> callShippingService(String endpoint, ShippingRequest request) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.post()
                .uri(endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorReturn(buildErrorResponse(request));    }

    private ShippingResponse buildErrorResponse(ShippingRequest request) {
        return ShippingResponse.create(
                request.getOrderId(),
                request.getQuantity(),
                Status.FAILED,
                null,
                null
        );
    }
}
