package com.bezdjian.trafiklab.service;

import com.bezdjian.trafiklab.client.TrafficLabClientService;
import com.bezdjian.trafiklab.exception.ClientException;
import com.bezdjian.trafiklab.model.BussStopPointsModel;
import com.bezdjian.trafiklab.model.JourneyPatternPointOnLine;
import com.bezdjian.trafiklab.model.JourneyPatternPointOnLineResults;
import com.bezdjian.trafiklab.model.StopPoint;
import com.bezdjian.trafiklab.model.TopTenListModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Service to find and retrieve JourneyPoints data.
 */
@Service
@CacheConfig(cacheNames = {"findStopsByLineNumber", "findLineWithMostStops"})
@Slf4j
public class TrafficService {

    private final TrafficLabClientService clientService;

    @Autowired
    public TrafficService(TrafficLabClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Method that finds all the stops by given bus line number
     * We do not want this to be invoked every time we refresh the page, so we cache it.
     *
     * @param lineNumber {@code Integer}
     * @return {@code List} of {@code BussStopPointsDTO}
     */
    public List<BussStopPointsModel> findStopsByLineNumber(int lineNumber) throws ClientException {

        JourneyPatternPointOnLine journeyPoint = clientService.getJourneyPoints();

        /* select j.lineNumber, s.stopPointName " +
                " from JourneyPointEntity AS j " +
                " join StopPointEntity AS s on s.stopPointNumber = j.journeyPatternPointNumber" +
                " where j.lineNumber = :lineNumber and j.directionCode = 1" +
                " group by s.stopAreaNumber"; */
        try {
            List<JourneyPatternPointOnLineResults> journeyPointsByLineNumber =
                getJourneyPointsByLineNumber(lineNumber, journeyPoint);

            return getStopPoints(journeyPointsByLineNumber);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClientException(e.getMessage());
        }
    }

    private List<BussStopPointsModel> getStopPoints(
        List<JourneyPatternPointOnLineResults> journeyPatternPointOnLineResults) throws
        ClientException {
        StopPoint stopPoint = clientService.getStopPoints();
        List<BussStopPointsModel> stopPointsModels = new ArrayList<>();
        stopPoint.getResponseData()
            .getResult()
            .stream()
            .filter(s -> journeyPatternPointOnLineResults.stream()
                .anyMatch(j -> j.getJourneyPatternPointNumber().equals(s.getStopPointNumber()))
            )
            .forEach(s -> stopPointsModels.add(BussStopPointsModel.builder()
                .lineNumber(journeyPatternPointOnLineResults.get(0).getLineNumber())
                .stopName(s.getStopPointName())
                .build()));
        return stopPointsModels;
    }

    private List<JourneyPatternPointOnLineResults> getJourneyPointsByLineNumber(int lineNumber,
                                                                                JourneyPatternPointOnLine journeyPoint) {
        return journeyPoint.getResponseData().getResult()
            .stream()
            .filter(s -> s.getLineNumber() == lineNumber)
            .filter(s -> s.getDirectionCode().equals("1"))
            .collect(Collectors.toList());
    }

    /**
     * Method that finds bus lines that have the most stops.
     * We do not want this to be invoked every time we refresh the page, so we cache it.
     *
     * @return {@code Map} of String and Object
     */
    public Map<String, Object> findLineWithMostStops() throws ClientException {
        Map<String, Object> topTenAndStopNames = new HashMap<>();
        HashMap<Integer, Long> lineNumbers = new HashMap<>(getAllLineNumbers());

        //Sort and retrieve top 10 line numbers with most stops.
        List<Map.Entry<Integer, Long>> topTenList = getTopTenMostStops(lineNumbers);
        List<TopTenListModel> topTenListModels = new ArrayList<>();
        topTenList.forEach(t -> topTenListModels.add(TopTenListModel.builder()
            .lineNumber(t.getKey())
            .stopCount(t.getValue().toString())
            .build()));
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
    private List<Map.Entry<Integer, Long>> getTopTenMostStops(HashMap<Integer, Long> map) {
        Set<Map.Entry<Integer, Long>> set = map.entrySet();
        List<Map.Entry<Integer, Long>> list = new ArrayList<>(set);
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        //If more than 10, retrieve top ten.
        if (list.size() > 10)
            return list.subList(0, 10);
        else
            return list;
    }

    /**
     * Private method that queries JourneyPoints and returns line numbers.
     *
     * @return {@code List} of JourneyPoint line numbers with stop counts.
     */
    private Map<Integer, Long> getAllLineNumbers() throws ClientException {
        return clientService.getJourneyPoints().getResponseData()
            .getResult()
            .stream()
            .filter(s -> s.getDirectionCode().equals("1"))
            .map(JourneyPatternPointOnLineResults::getLineNumber)
            //<lineNumber, number of stops>
            .collect(groupingBy(Integer::intValue, Collectors.counting()));
    }
}
