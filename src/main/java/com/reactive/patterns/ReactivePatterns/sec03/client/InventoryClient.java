package com.reactive.patterns.ReactivePatterns.sec03.client;

import com.reactive.patterns.ReactivePatterns.sec03.dto.InventoryRequest;
import com.reactive.patterns.ReactivePatterns.sec03.dto.InventoryResponse;
import com.reactive.patterns.ReactivePatterns.sec03.dto.PaymentResponse;
import com.reactive.patterns.ReactivePatterns.sec03.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InventoryClient {
    @Value("${sec03.inventory.service}")
    private String baseUrl;

    private static final String DEDUCT = "deduct";
    private static final String RESTORE = "restore";

    public Mono<InventoryResponse> deduct(InventoryRequest request) {
        return callInventoryService(DEDUCT, request);
    }

    public Mono<InventoryResponse> restore(InventoryRequest request) {
        return callInventoryService(RESTORE, request);
    }

    private Mono<InventoryResponse> callInventoryService(String endpoint, InventoryRequest request) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.post()
                .uri(endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .onErrorReturn(buildErrorResponse(request));
    }

    private InventoryResponse buildErrorResponse(InventoryRequest request) {
        return InventoryResponse.create(
                request.getProductId(),
                request.getQuantity(),
                -1,
                Status.FAILED
        );
    }
}
