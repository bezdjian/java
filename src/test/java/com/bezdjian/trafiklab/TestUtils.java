package com.bezdjian.trafiklab;

import com.bezdjian.trafiklab.model.BussStopPointsModel;
import com.bezdjian.trafiklab.model.JourneyPatternPointOnLine;
import com.bezdjian.trafiklab.model.JourneyPatternPointOnLineResults;
import com.bezdjian.trafiklab.model.JourneyResponseData;
import com.bezdjian.trafiklab.model.StopPoint;
import com.bezdjian.trafiklab.model.StopPointResponseData;
import com.bezdjian.trafiklab.model.StopPointResults;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@UtilityClass
public class TestUtils {

    public List<BussStopPointsModel> createBussStopsList(int size) {
        List<BussStopPointsModel> dtos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dtos.add(createBStopDTO(size));
        }
        return dtos;
    }

    private BussStopPointsModel createBStopDTO(int lineNumber) {
        return BussStopPointsModel.builder()
            .lineNumber(lineNumber)
            .stopName("here")
            .build();
    }

    public JourneyPatternPointOnLine getJourneyPatternPointOnLine() {
        return JourneyPatternPointOnLine.builder()
            .statusCode(0)
            .message("msg")
            .executionTime("21")
            .responseData(JourneyResponseData.builder()
                .result(createJourneyPatternPointOnLineResults())
                .type("JourneyPoint")
                .version("v1")
                .build())
            .build();
    }

    public List<JourneyPatternPointOnLineResults> createJourneyPatternPointOnLineResults() {
        return List.of(JourneyPatternPointOnLineResults.builder()
                .lineNumber(1720)
                .directionCode("1")
                .journeyPatternPointNumber("1720")
                .existsFromDate("2016")
                .lastModifiedUtcDateTime("2019")
                .build(),
            JourneyPatternPointOnLineResults.builder()
                .lineNumber(172)
                .directionCode("1")
                .journeyPatternPointNumber("172")
                .existsFromDate("2016")
                .lastModifiedUtcDateTime("2019")
                .build(),
            JourneyPatternPointOnLineResults.builder()
                .lineNumber(172)
                .directionCode("1")
                .journeyPatternPointNumber("172")
                .existsFromDate("2016")
                .lastModifiedUtcDateTime("2019")
                .build());
    }

    public StopPoint createStopPoints() {
        return StopPoint.builder()
            .statusCode(0)
            .message("msg")
            .executionTime("200")
            .responseData(StopPointResponseData.builder()
                .result(createStopPointResults())
                .type("StopPoint")
                .version("v1")
                .build())
            .build();
    }

    private Set<StopPointResults> createStopPointResults() {
        return Set.of(StopPointResults.builder()
                .stopPointNumber("172")
                .stopPointName("Flemingsberg Station")
                .stopAreaNumber("2124")
                .build(),
            StopPointResults.builder()
                .stopPointNumber("172")
                .stopPointName("Huddinge Station")
                .stopAreaNumber("2124")
                .build(),
            StopPointResults.builder()
                .stopPointNumber("1720")
                .stopPointName("Some other station")
                .stopAreaNumber("21240")
                .build());
    }

    public Map<String, Object> createMap() {
        return new HashMap<>() {{
            put("keyz", "valuez");
            put("keyz1", "valuez2");
        }};
    }
}
