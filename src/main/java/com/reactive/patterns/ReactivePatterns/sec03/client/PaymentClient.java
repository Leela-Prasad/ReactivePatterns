package com.reactive.patterns.ReactivePatterns.sec03.client;

import com.reactive.patterns.ReactivePatterns.sec03.dto.PaymentRequest;
import com.reactive.patterns.ReactivePatterns.sec03.dto.PaymentResponse;
import com.reactive.patterns.ReactivePatterns.sec03.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentClient {
    @Value("${sec03.user.service}")
    private String baseUrl;

    private static final String DEDUCT = "deduct";
    private static final String REFUND = "refund";
    public Mono<PaymentResponse> deduct(PaymentRequest request) {
       return callPaymentService(DEDUCT, request);
    }

    public Mono<PaymentResponse> refund(PaymentRequest request) {
        return callPaymentService(REFUND, request);
    }

    private Mono<PaymentResponse> callPaymentService(String endpoint, PaymentRequest request) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        return webClient.post()
                .uri(endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .onErrorReturn(buildErrorResponse(request));
    }

    private PaymentResponse buildErrorResponse(PaymentRequest request) {
        return PaymentResponse.create(
                -1,
                null,
                Status.FAILED,
                request.getUserId()
        );
    }
}
