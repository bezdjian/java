package com.bezdjian.trafiklab.service;

import com.bezdjian.trafiklab.model.BussStopPointsModel;
import com.bezdjian.trafiklab.model.TopTenListModel;
import com.bezdjian.trafiklab.repository.JourneyPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service to find and retrieve JourneyPoints data.
 */
@Service
@CacheConfig(cacheNames = {"trafikService"})
@Slf4j
public class TrafikService {

    private com.bezdjian.trafiklab.repository.JourneyPointRepository journeyPointRepository;

    @Autowired
    public TrafikService(JourneyPointRepository journeyPointRepository) {
        this.journeyPointRepository = journeyPointRepository;
    }

    /**
     * Method that finds all the stops by given bus line number
     * We do not want this to be invoked every time we refresh the page, so we cache it.
     *
     * @param lineNumber {@code Integer}
     * @return {@code List} of {@code BussStopPointsDTO}
     */
    @Cacheable
    public List<BussStopPointsModel> findStopsByLineNumber(int lineNumber) {
        //Here we can for example do some logic before invoking repository methods.
        List<BussStopPointsModel> dtos = journeyPointRepository.findStopsByLineNumber(lineNumber);
        log.info("***** {} stops found for buss line {} *****", dtos.size(), lineNumber);
        return dtos;
    }

    /**
     * Method that finds bus lines that have the most stops.
     * We do not want this to be invoked every time we refresh the page, so we cache it.
     *
     * @return {@code Map} of String and Object
     */
    @Cacheable
    public Map<String, Object> findLineWithMostStops() {
        Map<String, Object> topTenAndStopNames = new HashMap<>();
        HashMap<Integer, Integer> mostStops = new HashMap<>();
        List<Integer> pointEntities = getAllLineNumbers();

        //Loop over all the journeypoints, findstops by their line number to count the stops.
        pointEntities.forEach(p -> {
            List<BussStopPointsModel> dtos = findStopsByLineNumber(p);
            mostStops.put(p, dtos.size());
        });
        //Sort and retrieve top 10 line numbers with most stops.
        List<Map.Entry<Integer, Integer>> topTenList = getTopTenMostStops(mostStops);
        List<TopTenListModel> topTenListModels = new ArrayList<>();
        topTenList.forEach(t -> topTenListModels.add(new TopTenListModel(t.getKey(), t.getValue().toString())));
        topTenAndStopNames.put("topTenList", topTenListModels);
        //Get the stop names of the line number that has the most stops.
        //getKey() is the line number we want to search.
        if (!topTenList.isEmpty()) {
            List<BussStopPointsModel> stopNames = findStopsByLineNumber(topTenList.get(0).getKey());
            topTenAndStopNames.put("stopNames", stopNames);

            log.info("***** Top 10 lines with most stops: " + topTenList);
            log.info("***** Stop names of the most buss stop line: " + stopNames.size());
            return topTenAndStopNames;
        }
        return Collections.emptyMap();

    }

    /**
     * Private method that sorts and retrieves the top then lines that have the most stops
     *
     * @param map {@code Map}
     * @return {@code List} of Entries
     */
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

    /**
     * Private method that queries JourneyPoints and returns IDs.
     *
     * @return {@code List} of JourneyPoint IDs.
     */
    private List<Integer> getAllLineNumbers() {
        return journeyPointRepository.getAllLineNumbers();
    }
}
