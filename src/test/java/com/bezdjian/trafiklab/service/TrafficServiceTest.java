package com.bezdjian.trafiklab.service;

import com.bezdjian.trafiklab.TestUtils;
import com.bezdjian.trafiklab.client.TrafficLabClientService;
import com.bezdjian.trafiklab.model.BussStopPointsModel;
import com.bezdjian.trafiklab.repository.JourneyPointRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SuppressWarnings("unused")
public class TrafficServiceTest {

    @InjectMocks
    private TrafficService trafficService;
    @Mock
    private JourneyPointRepository repository;
    @Mock
    private TrafficLabClientService client;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void findStopsByLineNumber() {
        when(trafficService.findStopsByLineNumber(anyInt())).thenReturn(TestUtils.createBussStopsList(10));
        List<BussStopPointsModel> list = trafficService.findStopsByLineNumber(1);
        assertEquals("List is empty", TestUtils.createBussStopsList(10).size(), list.size());
    }

    @Test
    public void findLineWithMostStops_moreThanTen() {
        int size = 15;
        when(repository.getAllLineNumbers()).thenReturn(TestUtils.createLineNumbers(size));
        when(repository.findAll()).thenReturn(TestUtils.createJourneyPointList(size));
        when(repository.findStopsByLineNumber(anyInt())).thenReturn(TestUtils.createBussStopsList(size));
        Map<String, Object> map = trafficService.findLineWithMostStops();
        assertEquals("Map is wrong", 2, map.size());
    }

    @Test
    public void findLineWithMostStops_lessThanTen() {
        int size = 5;
        when(repository.getAllLineNumbers()).thenReturn(TestUtils.createLineNumbers(size));
        when(repository.findAll()).thenReturn(TestUtils.createJourneyPointList(size));
        when(repository.findStopsByLineNumber(anyInt())).thenReturn(TestUtils.createBussStopsList(size));
        Map<String, Object> map = trafficService.findLineWithMostStops();
        assertEquals("Map is wrong", 2, map.size());
    }

    @Test
    public void findLineWithMostStops_emptyMap() {
        int size = 0;
        when(repository.getAllLineNumbers()).thenReturn(TestUtils.createLineNumbers(size));
        when(repository.findAll()).thenReturn(TestUtils.createJourneyPointList(size));
        when(repository.findStopsByLineNumber(anyInt())).thenReturn(TestUtils.createBussStopsList(size));
        Map<String, Object> map = trafficService.findLineWithMostStops();
        assertEquals("Map is wrong", 0, map.size());
    }
}