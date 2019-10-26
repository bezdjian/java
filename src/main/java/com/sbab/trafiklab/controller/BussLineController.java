package com.sbab.trafiklab.controller;

import com.sbab.trafiklab.dto.BussStopPointsDTO;
import com.sbab.trafiklab.service.TrafikService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class BussLineController {

    private final TrafikService service;

    public BussLineController(TrafikService service) {
        this.service = service;
    }

    @GetMapping(value = "/getStops/{lineNumber}")
    public List<BussStopPointsDTO> findStopsByLineNumber(@PathVariable(name = "lineNumber") int lineNumber) {
        log.info("***** Gathering stop names for buss line {} *****", lineNumber);
        return service.findStopsByLineNumber(lineNumber);
    }

    @GetMapping("/getMostStops")
    public Map<String, Object> findLineWithMostStops(){
        log.info("***** Finding bus lines with the most stops *****");
        return service.findLineWithMostStops();
    }
}
