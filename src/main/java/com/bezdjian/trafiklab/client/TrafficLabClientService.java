package com.bezdjian.trafiklab.client;

import com.bezdjian.trafiklab.entity.JourneyPointEntity;
import com.bezdjian.trafiklab.entity.StopPointEntity;
import com.bezdjian.trafiklab.exception.ClientException;
import com.bezdjian.trafiklab.model.JourneyPatternPointOnLine;
import com.bezdjian.trafiklab.model.JourneyResponseData;
import com.bezdjian.trafiklab.model.StopPoint;
import com.bezdjian.trafiklab.model.StopPointResponseData;
import com.bezdjian.trafiklab.repository.JourneyPointRepository;
import com.bezdjian.trafiklab.repository.StopPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service to retrieve and save data from trafiklab's API
 */
@Service
@Slf4j
public class TrafficLabClientService {

    private final TrafficLabClient trafficLabClient;

    private final JourneyPointRepository journeyPointRepository;
    private final StopPointRepository stopPointRepository;
    private final String key;

    public TrafficLabClientService(TrafficLabClient trafficLabClient,
            JourneyPointRepository journeyPointRepository,
            StopPointRepository stopPointRepository,
            @Value("${trafiklab.api.key}") String key) {
        this.trafficLabClient = trafficLabClient;
        log.info("***** Initializing JourneyPatternPointOnLineAPI and calling Trafiklab's API *****");
        this.journeyPointRepository = journeyPointRepository;
        this.stopPointRepository = stopPointRepository;
        this.key = key;
    }

    /**
     * Method to call Journey pattern points from TrafikLab's API and save into DB
     *
     * @throws ClientException when there is nothing to save.
     */
    public void saveJourneyPointNumbers() throws ClientException {
        JourneyPatternPointOnLine journeys = trafficLabClient.getJourneyPatternPointOnLine(key);
        if (journeys != null && journeys.getResponseData() != null && !journeys.getResponseData()
                .getResult()
                .isEmpty()) {
            saveJourneyPoints(journeys.getResponseData());
        } else {
            throw new ClientException("JourneyPointNumbers could not be saved");
        }
    }

    /**
     * Method to call Stop points from TrafikLab's API and save into DB
     *
     * @throws ClientException when there is nothing to save.
     */
    public void saveStopPoints() throws ClientException {
        StopPoint stopPoint = trafficLabClient.getStopPoints(key);
        //Save
        if (stopPoint != null && stopPoint.getResponseData() != null && !stopPoint.getResponseData()
                .getResult()
                .isEmpty()) {
            saveStopPoints(stopPoint.getResponseData());
        } else {
            throw new ClientException("StopPoints could not be saved");
        }
    }

    /**
     * Private method to save in JourneyPointEntity
     *
     * @param responseData {@code JourneyResponseData}
     */
    private void saveJourneyPoints(JourneyResponseData responseData) {
        responseData.getResult()
                .forEach(e -> journeyPointRepository.save(new JourneyPointEntity(e)));
        log.info("***** JourneyPoints are saved!");
    }

    /**
     * Private method to save in StopPointEntity
     *
     * @param responseData {@code StopPointResponseData}
     */
    private void saveStopPoints(StopPointResponseData responseData) {
        responseData.getResult()
                .forEach(r -> stopPointRepository.save(new StopPointEntity(r)));
        log.info("***** StopPoints are saved!");
    }
}
