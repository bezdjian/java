package com.sbab.trafiklab.service;

import com.sbab.trafiklab.BaseTest;
import com.sbab.trafiklab.api.TrafikLabClient;
import com.sbab.trafiklab.model.BussStopPointsModel;
import com.sbab.trafiklab.repository.JourneyPointRepository;
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
public class TrafikServiceTest extends BaseTest {

    @InjectMocks
    private TrafikService trafikService;
    @Mock
    private JourneyPointRepository repository;
    @Mock
    private TrafikLabClient client;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void findStopsByLineNumber() {
        when(trafikService.findStopsByLineNumber(anyInt())).thenReturn(createBussStopsList(10));
        List<BussStopPointsModel> list = trafikService.findStopsByLineNumber(1);
        assertEquals("List is empty", createBussStopsList(10).size(), list.size());
    }

    @Test
    public void findLineWithMostStops_moreThanTen() {
        int size = 15;
        when(repository.getAllLineNumbers()).thenReturn(createLineNumbers(size));
        when(repository.findAll()).thenReturn(createJourneyPointList(size));
        when(repository.findStopsByLineNumber(anyInt())).thenReturn(createBussStopsList(size));
        Map<String, Object> map = trafikService.findLineWithMostStops();
        assertEquals("Map is wrong", 2, map.size());
    }

    @Test
    public void findLineWithMostStops_lessThanTen() {
        int size = 5;
        when(repository.getAllLineNumbers()).thenReturn(createLineNumbers(size));
        when(repository.findAll()).thenReturn(createJourneyPointList(size));
        when(repository.findStopsByLineNumber(anyInt())).thenReturn(createBussStopsList(size));
        Map<String, Object> map = trafikService.findLineWithMostStops();
        assertEquals("Map is wrong", 2, map.size());
    }

    @Test
    public void findLineWithMostStops_emptyMap(){
        int size = 0;
        when(repository.getAllLineNumbers()).thenReturn(createLineNumbers(size));
        when(repository.findAll()).thenReturn(createJourneyPointList(size));
        when(repository.findStopsByLineNumber(anyInt())).thenReturn(createBussStopsList(size));
        Map<String, Object> map = trafikService.findLineWithMostStops();
        assertEquals("Map is wrong", 0, map.size());
    }
}