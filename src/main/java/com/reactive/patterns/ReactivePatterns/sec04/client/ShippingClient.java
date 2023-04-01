package com.reactive.patterns.ReactivePatterns.sec04.client;

import com.reactive.patterns.ReactivePatterns.sec04.dto.ShippingRequest;
import com.reactive.patterns.ReactivePatterns.sec04.dto.ShippingResponse;
import com.reactive.patterns.ReactivePatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ShippingClient {
    @Value("${sec04.shipping.service}")
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
                null,
                request.getQuantity(),
                Status.FAILED,
                null,
                null
        );
    }
}
