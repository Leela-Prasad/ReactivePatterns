package com.reactive.patterns.ReactivePatterns.sec02.controller;

import com.reactive.patterns.ReactivePatterns.sec02.dto.FlightResult;
import com.reactive.patterns.ReactivePatterns.sec02.service.FlightSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class FlightsController {

    @Autowired
    private FlightSearchService flightSearchService;

    @GetMapping(value = "/sec02/flights/{from}/{to}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FlightResult> getFlights(@PathVariable String from, @PathVariable String to) {
        return flightSearchService.getFlights(from, to);
    }
}
