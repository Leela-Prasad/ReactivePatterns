package com.reactive.patterns.ReactivePatterns.sec03.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactive.patterns.ReactivePatterns.sec03.dto.OrchestrationRequestContext;

public class DebugUtil {

    public static void print(OrchestrationRequestContext context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(context);
            System.out.println(s);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
