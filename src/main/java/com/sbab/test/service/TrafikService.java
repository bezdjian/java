package com.sbab.test.service;

import com.sbab.test.api.TrafikLabAPIService;
import com.sbab.test.dto.BussStopPointsDTO;
import com.sbab.test.repository.JourneyPointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service to find and retrieve JourneyPoints data.
 */
@Service
@CacheConfig(cacheNames = {"trafikService"})
public class TrafikService {

    private static final Logger logger = LoggerFactory.getLogger(TrafikService.class);
    private final JourneyPointRepository journeyPointRepository;
    private final TrafikLabAPIService service;

    public TrafikService(JourneyPointRepository journeyPointRepository, TrafikLabAPIService service) throws Exception {
        this.journeyPointRepository = journeyPointRepository;
        this.service = service;
        saveAllPoints();
    }

    private void saveAllPoints() throws Exception {
        service.saveJourneyPointNumbers();
        service.saveStopPoints();
    }

    //We do not want this to be invoked every time we refresh the page, so cache it!.
    @Cacheable
    public List<BussStopPointsDTO> findStopsByLineNumber(int lineNumber) {
        //Here we can for example do some logic before invoking repository methods.
        List<BussStopPointsDTO> dtos = journeyPointRepository.findStopsByLineNumber(lineNumber);
        logger.info("***** {} stops found for buss line {} *****", dtos.size(), lineNumber);
        return dtos;
    }

    //We do not want this to be invoked every time we refresh the page, so cache it!.
    @Cacheable
    public Map<String, Object> findLineWithMostStops() {
        Map<String, Object> topTenAndStopNames = new HashMap<>();
        HashMap<Integer, Integer> mostStops = new HashMap<>();
        List<Integer> pointEntities = getAllLineNumbers();

        //Loop over all the journeypoints, findstops by their line number to count the stops.
        pointEntities.forEach(p -> {
            List<BussStopPointsDTO> dtos = findStopsByLineNumber(p);
            mostStops.put(p, dtos.size());
        });
        //Sort and retrieve top 10 line numbers with most stops.
        List<Map.Entry<Integer, Integer>> topTenList = getTopTenMostStops(mostStops);
        topTenAndStopNames.put("topTenList", topTenList);
        //Get the stop names of the line number that has the most stops.
        //getKey() is the line number we want to search.
        if (!topTenList.isEmpty()) {
            List<BussStopPointsDTO> stopNames = findStopsByLineNumber(topTenList.get(0).getKey());
            topTenAndStopNames.put("stopNames", stopNames);

            logger.info("***** Top 10 lines with most stops: " + topTenList);
            logger.info("***** Stop names of the most buss stop line: " + stopNames.size());
            return topTenAndStopNames;
        }
        return Collections.emptyMap();

    }

    private List<Map.Entry<Integer, Integer>> getTopTenMostStops(HashMap<Integer, Integer> map) {
        Set<Map.Entry<Integer, Integer>> set = map.entrySet();
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(set);
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        //If more than 10, retrieve top ten.
        if (list.size() > 10)
            return list.subList(0, 10);
        else
            return list;
    }

    private List<Integer> getAllLineNumbers() {
        return journeyPointRepository.getAllLineNumbers();
    }
}
