package com.bezdjian.trafiklab.service;

import com.bezdjian.trafiklab.TestUtils;
import com.bezdjian.trafiklab.client.TrafficLabClientService;
import com.bezdjian.trafiklab.exception.ClientException;
import com.bezdjian.trafiklab.model.BussStopPointsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TrafficServiceTest {

    @InjectMocks
    private TrafficService trafficService;
    @Mock
    private TrafficLabClientService client;

    @BeforeEach
    void setUp() throws ClientException {
        openMocks(this);
        when(client.getJourneyPoints()).thenReturn(Mono.just(TestUtils.getJourneyPatternPointOnLine()));
        when(client.getStopPoints()).thenReturn(Mono.just(TestUtils.createStopPoints()));
    }

    @Test
    void findStopsByLineNumber() throws ClientException {
        List<BussStopPointsModel> list = trafficService.findStopsByLineNumber(172).block();
        assertNotNull(list);
        System.out.println("List: " + list);
        assertEquals(2, list.size());
    }

    @Test
    void findLineWithMostStopsMoreThanTen() throws ClientException {
        Map<String, Object> map = trafficService.findLineWithMostStops().block();
        assertNotNull(map);
        assertEquals(2, map.size());
    }
}