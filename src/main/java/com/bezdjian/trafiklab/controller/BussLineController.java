package com.bezdjian.trafiklab.controller;

import com.bezdjian.trafiklab.service.TrafficService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BussLineController {

    private final TrafficService service;

    @GetMapping(value = "/getStops/{lineNumber}")
    public Mono<ResponseEntity<Object>> findStopsByLineNumber(
        @PathVariable(name = "lineNumber") int lineNumber) {
        long startTime = System.currentTimeMillis();
        return service.findStopsByLineNumber(lineNumber)
            .doOnSubscribe(s -> log.info("***** Finding stops by line number {} *****", lineNumber))
            .doOnSuccess(s -> {
                long endTime = System.currentTimeMillis();
                long totalTime = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
                log.info("***** Buss line {} has {} stops. Took {} seconds", lineNumber, s.size(), totalTime);
            })
            .doOnError(e -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage()))
            .map(ResponseEntity::ok);
    }

    @GetMapping("/getMostStops")
    public Mono<ResponseEntity<Object>> findLineWithMostStops() {
        long startTime = System.currentTimeMillis();
        return service.findLineWithMostStops()
            .doOnSubscribe(s -> log.info("***** Finding bus lines with the most stops *****"))
            .doOnSuccess(s -> {
                long endTime = System.currentTimeMillis();
                long totalTime = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
                log.info("*** findLineWithMostStops took {} seconds", totalTime);
            })
            .doOnError(e -> {
                log.error("Error while finding line with most stop: {}", e.getMessage(), e);
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
            })
            .map(ResponseEntity::ok);
    }
}
