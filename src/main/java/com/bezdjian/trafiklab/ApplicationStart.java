package com.bezdjian.trafiklab;

import com.bezdjian.trafiklab.client.TrafficLabClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationStart implements CommandLineRunner {
    private final TrafficLabClientService trafficLabClientService;

    @Autowired
    public ApplicationStart(TrafficLabClientService trafficLabClientService) {
        this.trafficLabClientService = trafficLabClientService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("***** Initializing TrafikService and saving stop and journey points *****");
        trafficLabClientService.saveJourneyPointNumbers();
        trafficLabClientService.saveStopPoints();
    }
}
