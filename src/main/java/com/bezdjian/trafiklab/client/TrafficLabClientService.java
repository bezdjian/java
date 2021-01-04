package com.bezdjian.trafiklab.client;

import com.bezdjian.trafiklab.exception.ClientException;
import com.bezdjian.trafiklab.model.JourneyPatternPointOnLine;
import com.bezdjian.trafiklab.model.StopPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Service to retrieve and save data from trafiklab's API
 */
@Service
@CacheConfig(cacheNames = {"StopPoints", "JourneyPoints"})
@Slf4j
public class TrafficLabClientService {

    private final TrafficLabClient trafficLabClient;
    private final String key;

    public TrafficLabClientService(TrafficLabClient trafficLabClient, @Value("${trafiklab.api.key}") String key) {
        this.trafficLabClient = trafficLabClient;
        this.key = key;
    }

    /**
     * Method to call Journey pattern points from TrafikLab's API and save into DB
     *
     * @return JourneyPatternPointOnLine
     * @throws ClientException when there is nothing to save.
     */
    @Cacheable(cacheNames = "JourneyPoints")
    public JourneyPatternPointOnLine getJourneyPoints() throws ClientException {
        log.info("Getting journey points...");
        try {
            return trafficLabClient.getJourneyPatternPointOnLine(key);
        } catch (Exception f) {
            f.printStackTrace();
            throw new ClientException(f.getMessage());
        }
    }

    /**
     * Method to call Stop points from TrafikLab's API and save into DB
     *
     * @return StopPoint
     * @throws ClientException when there is nothing to save.
     */
    @Cacheable(cacheNames = "StopPoints")
    public StopPoint getStopPoints() throws ClientException {
        log.info("Getting stop points...");
        try {
            return trafficLabClient.getStopPoints(key);
        } catch (Exception f) {
            f.printStackTrace();
            throw new ClientException(f.getMessage());
        }
    }
}
