package com.sbab.trafiklab.api;

import com.sbab.trafiklab.entity.JourneyPointEntity;
import com.sbab.trafiklab.entity.StopPointEntity;
import com.sbab.trafiklab.dto.JourneyPatternPointOnLine;
import com.sbab.trafiklab.dto.JourneyResponseData;
import com.sbab.trafiklab.dto.StopPoint;
import com.sbab.trafiklab.dto.StopPointResponseData;
import com.sbab.trafiklab.repository.JourneyPointRepository;
import com.sbab.trafiklab.repository.StopPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Client to retrieve and save data from trafiklab's API
 */
@Service
@Slf4j
public class TrafikLabClient {

    @Autowired
    private JourneyPointRepository journeyPointRepository;
    @Autowired
    private StopPointRepository stopPointRepository;

    private String key;
    private String url;

    private RestTemplate restTemplate = new RestTemplate();

    public TrafikLabClient(@Value("${trafiklab.api.key}") String key, @Value("${trafiklab.api.url}") String url) {
        log.info("***** Initializing JourneyPatternPointOnLineAPI and calling Trafiklab's API *****");
        this.key = key;
        this.url = url;
    }

    /**
     * Method to call Journey pattern points from TrafikLab's API and save into DB
     * 
     * @throws Exception when there is nothing to save.
     */
    public void saveJourneyPointNumbers() throws Exception {
        String fullUrl = url + "model=jour&key=" + key;
        log.info("***** Calling " + fullUrl + " to get journeyPoints");

        JourneyPatternPointOnLine journeys = restTemplate.getForObject(fullUrl, JourneyPatternPointOnLine.class);
        if (journeys != null && journeys.getResponseData() != null
                && !journeys.getResponseData().getResult().isEmpty()) {
            saveJourneyPoints(journeys.getResponseData());
        } else {
            throw new Exception("JourneyPointNumbers could not be saved");
        }
    }

    /**
     * Method to call Stop points from TrafikLab's API and save into DB
     * 
     * @throws Exception when there is nothing to save.
     */
    public void saveStopPoints() throws Exception {
        String fullUrl = url + "model=stop&key=" + key;
        log.info("***** Calling " + fullUrl + " to get stopPoints");

        StopPoint stopPoint = restTemplate.getForObject(fullUrl, StopPoint.class);
        // Save
        if (stopPoint != null && stopPoint.getResponseData() != null
                && !stopPoint.getResponseData().getResult().isEmpty()) {
            saveStopPoints(stopPoint.getResponseData());
        } else {
            throw new Exception("StopPoints could not be saved");
        }
    }

    /**
     * Private method to save in JourneyPointEntity
     * 
     * @param responseData {@code JourneyResponseData}
     */
    private void saveJourneyPoints(JourneyResponseData responseData) {
        responseData.getResult().forEach(e -> journeyPointRepository.save(new JourneyPointEntity(e)));
        log.info("***** JourneyPoints are saved!");
    }

    /**
     * Private method to save in StopPointEntity
     * 
     * @param responseData {@code StopPointResponseData}
     */
    private void saveStopPoints(StopPointResponseData responseData) {
        responseData.getResult().forEach(r -> stopPointRepository.save(new StopPointEntity(r)));
        log.info("***** StopPoints are saved!");
    }
}
