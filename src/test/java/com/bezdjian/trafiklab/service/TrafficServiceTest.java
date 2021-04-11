package com.bezdjian.trafiklab.service;

import com.bezdjian.trafiklab.TestUtils;
import com.bezdjian.trafiklab.client.TrafficLabClientService;
import com.bezdjian.trafiklab.exception.ClientException;
import com.bezdjian.trafiklab.model.BussStopPointsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.MockitoAnnotations.openMocks;

class TrafficServiceTest {

    @InjectMocks
    private TrafficService trafficService;
    @Mock
    private TrafficLabClientService client;

    @BeforeEach
    void setUp() throws ClientException {
        openMocks(this);
        when(client.getJourneyPoints()).thenReturn(TestUtils.getJourneyPatternPointOnLine());
        when(client.getStopPoints()).thenReturn(TestUtils.createStopPoints());
    }

    @Test
    void findStopsByLineNumber() throws ClientException {
        List<BussStopPointsModel> list = trafficService.findStopsByLineNumber(172);
        System.out.println("List: " + list.toString());
        assertEquals(2, list.size());
    }

    @Test
    void findLineWithMostStopsMoreThanTen() throws ClientException {
        Map<String, Object> map = trafficService.findLineWithMostStops();
        assertEquals(2, map.size());
    }
}