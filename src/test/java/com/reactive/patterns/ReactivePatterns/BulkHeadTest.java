package com.reactive.patterns.ReactivePatterns;

import com.reactive.patterns.ReactivePatterns.sec10.dto.ProductAggregate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BulkHeadTest {

    private WebClient webClient;
    @BeforeAll
    public void setClient() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/sec10/")
                .build();
    }

    @Test
    public void createConcurrentUsers() {
        StepVerifier.create(
                Flux.merge(fibRequests(), productRequests()))
                .verifyComplete();
    }

    private Mono<Void> fibRequests() {
       return Flux.range(1, 40)
                .flatMap(i -> webClient.get().uri("fib/42").retrieve().bodyToFlux(Long.class))
                .doOnNext(this::print)
                .then();
    }

    private Mono<Void> productRequests() {
        return Mono.delay(Duration.ofMillis(100))
                        .thenMany(Flux.range(1, 40))
                .flatMap(i -> webClient.get().uri("product/1").retrieve().bodyToFlux(ProductAggregate.class))
                .map(ProductAggregate::getCategory)
                .doOnNext(this::print)
                .then();
    }

    private void print(Object o) {
        System.out.println(LocalDateTime.now() + " : " + o);
    }
}
