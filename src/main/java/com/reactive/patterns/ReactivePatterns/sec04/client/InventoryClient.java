package com.reactive.patterns.ReactivePatterns.sec04.client;

import com.reactive.patterns.ReactivePatterns.sec04.dto.InventoryRequest;
import com.reactive.patterns.ReactivePatterns.sec04.dto.InventoryResponse;
import com.reactive.patterns.ReactivePatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InventoryClient {
    @Value("${sec04.inventory.service}")
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
                null,
                request.getProductId(),
                request.getQuantity(),
                -1,
                Status.FAILED
        );
    }
}
