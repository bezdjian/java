package com.bezdjian.trafiklab;

import com.bezdjian.trafiklab.dto.BussStopPointsDTO;
import com.bezdjian.trafiklab.entity.JourneyPointEntity;
import com.bezdjian.trafiklab.pojo.*;

import java.util.*;

public class BaseTest {

    protected List<BussStopPointsDTO> createBussStopsList(int size) {
        List<BussStopPointsDTO> dtos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dtos.add(createBStopDTO(size));
        }
        return dtos;
    }

    private BussStopPointsDTO createBStopDTO(int l) {
        return new BussStopPointsDTO(l, "here");
    }

    protected List<JourneyPointEntity> createJourneyPointList(int size) {
        List<JourneyPointEntity> jpEntity = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            jpEntity.add(
                    new JourneyPointEntity(i, "1", String.valueOf(i),
                            "2019", "2005"));
        }
        return jpEntity;
    }

    protected List<Integer> createLineNumbers(int size) {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            integerList.add(i);
        }
        return integerList;
    }

    protected JourneyPatternPointOnLine getJourneyPatternPointOnLine(){
        return new JourneyPatternPointOnLine(0, "msg", "212",
                new JourneyResponseData(Collections.singletonList(createJourneyPatternPointOnLineResults()), "v", "type"));
    }

    private JourneyPatternPointOnLineResults createJourneyPatternPointOnLineResults(){
        return new JourneyPatternPointOnLineResults(172, "1", "22", "2019", "2016");
    }

    protected StopPoint createStopPoints(){
        return new StopPoint(0, "msgs", "214",
                new StopPointResponseData(Collections.singletonList(createStopPointResults()), "v3", "stoppointType"));
    }
    private StopPointResults createStopPointResults(){
        return new StopPointResults("1","point name", "2124", "10.1", "54.4", "ZN", "areatype");
    }

    protected Map<String, Object> createMap(){
        return new HashMap<String, Object>(){{
            put("keyz", "valuez");
            put("keyz1", "valuez2");
        }};
    }
}
