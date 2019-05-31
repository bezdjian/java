package com.sbab.test.api;

import com.sbab.test.entity.JourneyPointEntity;
import com.sbab.test.entity.StopPointEntity;
import com.sbab.test.pojo.JourneyPatternPointOnLine;
import com.sbab.test.pojo.JourneyResponseData;
import com.sbab.test.pojo.StopPoint;
import com.sbab.test.pojo.StopPointResponseData;
import com.sbab.test.repository.JourneyPointRepository;
import com.sbab.test.repository.StopPointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service to retrieve and save data from trafiklab's API
 */
@Service
public class TrafikLabAPIService {

    private static final Logger logger = LoggerFactory.getLogger(TrafikLabAPIService.class);

    @Autowired
    private JourneyPointRepository journeyPointRepository;
    @Autowired
    private StopPointRepository stopPointRepository;

    //Injection of @Value on field works after construction, having it inside construction parameter works in @Service
    private String key;
    private String url;

    private RestTemplate restTemplate = new RestTemplate();

    public TrafikLabAPIService(@Value("${trafiklab.api.key}") String key,
                               @Value("${trafiklab.api.url}") String url) {
        logger.info("***** Initializing JourneyPatternPointOnLineAPI and calling Trafiklab's API *****");
        this.key = key;
        this.url = url;
    }

    public void saveJourneyPointNumbers() throws Exception {
        String fullUrl = url + "model=jour&key=" + key;
        logger.info("***** Calling " + fullUrl + " to get journeyPoints");

        JourneyPatternPointOnLine journeys = restTemplate.getForObject(fullUrl, JourneyPatternPointOnLine.class);
        if (journeys != null && journeys.getResponseData() != null && !journeys.getResponseData().getResult().isEmpty()) {
            saveJourneyPoints(journeys.getResponseData());
        } else {
            throw new Exception("JourneyPointNumbers could not be saved");
        }
    }

    public void saveStopPoints() throws Exception {
        String fullUrl = url + "model=stop&key=" + key;
        logger.info("***** Calling " + fullUrl + " to get stopPoints");

        StopPoint stopPoint = restTemplate.getForObject(fullUrl, StopPoint.class);
        //Save
        if (stopPoint != null && stopPoint.getResponseData() != null && !stopPoint.getResponseData().getResult().isEmpty()) {
            saveStopPoints(stopPoint.getResponseData());
        } else {
            throw new Exception("StopPoints could not be saved");
        }
    }

    private void saveJourneyPoints(JourneyResponseData responseData) {
        responseData.getResult()
                .forEach(e -> journeyPointRepository.save(new JourneyPointEntity(e)));
        logger.info("***** JourneyPoints are saved!");
    }

    private void saveStopPoints(StopPointResponseData responseData) {
        responseData.getResult()
                .forEach(r -> stopPointRepository.save(new StopPointEntity(r)));
        logger.info("***** StopPoints are saved!");
    }
}
