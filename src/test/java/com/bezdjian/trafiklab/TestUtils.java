package com.bezdjian.trafiklab;

import com.bezdjian.trafiklab.entity.JourneyPointEntity;
import com.bezdjian.trafiklab.model.BussStopPointsModel;
import com.bezdjian.trafiklab.model.JourneyPatternPointOnLine;
import com.bezdjian.trafiklab.model.JourneyPatternPointOnLineResults;
import com.bezdjian.trafiklab.model.JourneyResponseData;
import com.bezdjian.trafiklab.model.StopPoint;
import com.bezdjian.trafiklab.model.StopPointResponseData;
import com.bezdjian.trafiklab.model.StopPointResults;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class TestUtils {

    public List<BussStopPointsModel> createBussStopsList(int size) {
        List<BussStopPointsModel> dtos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dtos.add(createBStopDTO(size));
        }
        return dtos;
    }

    private BussStopPointsModel createBStopDTO(int l) {
        return new BussStopPointsModel(l, "here");
    }

    public List<JourneyPointEntity> createJourneyPointList(int size) {
        List<JourneyPointEntity> jpEntity = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            jpEntity.add(
                    new JourneyPointEntity(i, "1", String.valueOf(i),
                            "2019", "2005"));
        }
        return jpEntity;
    }

    public List<Integer> createLineNumbers(int size) {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            integerList.add(i);
        }
        return integerList;
    }

    public JourneyPatternPointOnLine getJourneyPatternPointOnLine() {
        return new JourneyPatternPointOnLine(0, "msg", "212",
                new JourneyResponseData("version", "type",
                        Collections.singletonList(createJourneyPatternPointOnLineResults())));
    }

    private JourneyPatternPointOnLineResults createJourneyPatternPointOnLineResults() {
        return new JourneyPatternPointOnLineResults(172, "1", "22", "2019", "2016");
    }

    public StopPoint createStopPoints() {
        return new StopPoint(0, "msgs", "214",
                new StopPointResponseData("v3", "stoppointType", Collections.singletonList(createStopPointResults())));
    }

    private StopPointResults createStopPointResults() {
        return new StopPointResults("1", "point name", "2124", "10.1", "54.4", "ZN", "areatype");
    }

    public Map<String, Object> createMap() {
        return new HashMap<String, Object>() {{
            put("keyz", "valuez");
            put("keyz1", "valuez2");
        }};
    }
}
