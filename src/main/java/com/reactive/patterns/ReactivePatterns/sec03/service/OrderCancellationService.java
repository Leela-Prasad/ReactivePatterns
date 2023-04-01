package com.reactive.patterns.ReactivePatterns.sec03.service;

import com.reactive.patterns.ReactivePatterns.sec03.dto.OrchestrationRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class OrderCancellationService {
    @Autowired
    private List<Orchestrator> orchestrators;

    public void cancelOrder(OrchestrationRequestContext context) {
        orchestrators.stream()
                .forEach(o -> o.cancel(context));
    }
}
