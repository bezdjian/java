package com.bezdjian.trafiklab.client;

import com.bezdjian.trafiklab.exception.ClientException;
import com.bezdjian.trafiklab.model.JourneyPatternPointOnLine;
import com.bezdjian.trafiklab.model.StopPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Service to retrieve and save data from trafiklab's API
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"StopPoints", "JourneyPoints"})
public class TrafficLabClientService {

    private final TrafficClient trafficClient;

    /**
     * Method to call Journey pattern points from TrafikLab's API and save into DB
     *
     * @return JourneyPatternPointOnLine
     */
    @Cacheable(cacheNames = "JourneyPoints")
    public Mono<JourneyPatternPointOnLine> getJourneyPoints() {
        log.info("Getting journey points...");
        return trafficClient.getJourneyPatternPointOnLine()
            .doOnSuccess(jppo -> log.info("Got Journey points {}: {}", jppo.getStatusCode(), jppo.getExecutionTime()))
            .doOnError(e -> Mono.error(() -> {
                log.error("Error while getting journey points: {}", e.getMessage(), e);
                return new ClientException(e.getMessage());
            }))
            .cache(Duration.ofDays(1));
    }

    /**
     * Method to call Stop points from TrafikLab's API and save into DB
     *
     * @return StopPoint
     */
    @Cacheable(cacheNames = "StopPoints")
    public Mono<StopPoint> getStopPoints() {
        log.info("Getting stop points...");
        return trafficClient.getStopPoints()
            .doOnSuccess(stopPoint -> log.info("Got stop points: {}, {}",
                stopPoint.getStatusCode(), stopPoint.getExecutionTime()))
            .doOnError(e -> Mono.error(() -> new ClientException("Could not get stop points" + e.getMessage())))
            .cache(Duration.ofDays(1));
    }
}
