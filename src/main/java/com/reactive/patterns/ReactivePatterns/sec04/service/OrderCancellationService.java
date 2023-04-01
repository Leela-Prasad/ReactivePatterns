package com.reactive.patterns.ReactivePatterns.sec04.service;

import com.reactive.patterns.ReactivePatterns.sec04.dto.OrchestrationRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderCancellationService {
    @Autowired
    private List<Orchestrator> orchestrators;

    public void cancelOrder(OrchestrationRequestContext context) {
        orchestrators.stream()
                .forEach(o -> o.cancel(context));
    }
}
