package com.bezdjian.trafiklab.controller;

import com.bezdjian.trafiklab.exception.ClientException;
import com.bezdjian.trafiklab.model.BussStopPointsModel;
import com.bezdjian.trafiklab.service.TrafficService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class BussLineController {

    private final TrafficService service;

    @Autowired
    public BussLineController(TrafficService service) {
        this.service = service;
    }

    @GetMapping(value = "/getStops/{lineNumber}")
    public ResponseEntity<Object> findStopsByLineNumber(
            @PathVariable(name = "lineNumber") int lineNumber) {
        try {
            List<BussStopPointsModel> stopsByLineNumber = service.findStopsByLineNumber(lineNumber);
            log.info("***** Buss line {} has {} stops", lineNumber, stopsByLineNumber.size());
            return ResponseEntity.ok(stopsByLineNumber);
        } catch (ClientException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getMostStops")
    public ResponseEntity<Object> findLineWithMostStops() {
        log.info("***** Finding bus lines with the most stops *****");
        try {
            Map<String, Object> lineWithMostStops = service.findLineWithMostStops();
            return ResponseEntity.ok(lineWithMostStops);
        } catch (ClientException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
