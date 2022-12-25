package com.bezdjian.trafiklab.service;

import com.bezdjian.trafiklab.client.TrafficLabClientService;
import com.bezdjian.trafiklab.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
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
    public Mono<List<BussStopPointsModel>> findStopsByLineNumber(int lineNumber) {

        Mono<List<JourneyPatternPointOnLineResults>> journeyPointsByLineNumber =
            getJourneyPointsByLineNumber(lineNumber, clientService.getJourneyPoints());

        return getStopPoints(journeyPointsByLineNumber)
            //distinct(), otherwise it would double the list size with double stop names. (2 directions)
            .map(bussStops -> bussStops.stream().distinct().toList())
            .doOnError(e -> log.error(e.getMessage()));
    }

    private Mono<List<BussStopPointsModel>> getStopPoints(
        Mono<List<JourneyPatternPointOnLineResults>> journeyPatternPointOnLineResults) {

        Mono<StopPoint> stopPoints = clientService.getStopPoints();

        return Mono.zip(stopPoints, journeyPatternPointOnLineResults, (s, j) ->
            s.getResponseData()
                .getResult()
                .stream()
                .filter(s1 -> j.stream().anyMatch(j1 -> j1.getJourneyPatternPointNumber().equals(s1.getStopPointNumber())))
                .map(s1 -> BussStopPointsModel.builder()
                    .lineNumber(j.get(0).getLineNumber())
                    .stopName(s1.getStopPointName())
                    .build())
                .collect(Collectors.toList()));
    }

    private Mono<List<JourneyPatternPointOnLineResults>> getJourneyPointsByLineNumber(int lineNumber,
                                                                                      Mono<JourneyPatternPointOnLine> journeyPoint) {
        return journeyPoint.map(jppo -> jppo.getResponseData()
            .getResult()
            .stream()
            .filter(s -> s.getLineNumber() == lineNumber)
            .filter(s -> s.getDirectionCode().equals("1"))
            .collect(Collectors.toList()));
    }

    /**
     * Method that finds bus lines that have the most stops.
     * We do not want this to be invoked every time we refresh the page, so we cache it.
     *
     * @return {@code Map} of String and Object
     */
    public Mono<Map<String, Object>> findLineWithMostStops() {
        //Sort and retrieve top 10 line numbers with most stops.
        return getTopTenMostStops(getAllLineNumbers())
            .flatMap(topTenList -> {
                Mono<Map<String, Object>> mapMono = Mono.empty();
                Map<String, Object> topTenAndStopNames = new HashMap<>();
                List<TopTenListModel> topTenListModels = new ArrayList<>();

                topTenList.forEach(t -> topTenListModels.add(TopTenListModel.builder()
                    .lineNumber(t.getKey())
                    .stopCount(t.getValue().toString())
                    .build()));
                topTenAndStopNames.put("topTenList", topTenListModels);
                log.info("***** Top 10 lines with most stops: " + topTenList);

                //Get the stop names of the line number that has the most stops.
                //getKey() is the line number we want to search.
                if (!topTenList.isEmpty()) {
                    mapMono = findStopsByLineNumber(topTenList.get(0).getKey())
                        .map(stopNames -> {
                            topTenAndStopNames.put("stopNames", stopNames);
                            topTenAndStopNames.put("mostStopNamesLineNumber", stopNames.get(0).getLineNumber());
                            log.info("***** Number of stop names of the most buss stop line: " + stopNames.size());
                            return topTenAndStopNames;
                        });
                }
                return mapMono;
            });
    }

    /**
     * Private method that sorts and retrieves the top then lines that have the most stops
     *
     * @param lineNumbers {@code Map}
     * @return {@code List} of Entries
     */
    private Mono<List<Map.Entry<Integer, Long>>> getTopTenMostStops(Mono<Map<Integer, Long>> lineNumbers) {
        return lineNumbers.map(m -> {
            Set<Map.Entry<Integer, Long>> set = m.entrySet();
            List<Map.Entry<Integer, Long>> list = new ArrayList<>(set);
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            //If more than 10, retrieve top ten.
            if (list.size() > 10)
                return list.subList(0, 10);
            else
                return list;
        });
    }

    /**
     * Private method that queries JourneyPoints and returns line numbers.
     *
     * @return {@code List} of JourneyPoint line numbers with stop counts.
     */
    private Mono<Map<Integer, Long>> getAllLineNumbers() {
        return clientService.getJourneyPoints()
            .map(jp -> jp.getResponseData()
                .getResult()
                .stream()
                .filter(s -> s.getDirectionCode().equals("1"))
                .map(JourneyPatternPointOnLineResults::getLineNumber)
                //<lineNumber, number of stops>
                .collect(groupingBy(Integer::intValue, Collectors.counting())));
    }
}
