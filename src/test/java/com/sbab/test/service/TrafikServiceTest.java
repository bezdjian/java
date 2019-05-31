package com.sbab.test.service;

import com.sbab.test.BaseTest;
import com.sbab.test.api.TrafikLabAPIService;
import com.sbab.test.dto.BussStopPointsDTO;
import com.sbab.test.repository.JourneyPointRepository;
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
    private TrafikService service;
    @Mock
    private JourneyPointRepository repository;
    @Mock
    private TrafikLabAPIService api;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void findStopsByLineNumber() {
        when(service.findStopsByLineNumber(anyInt())).thenReturn(createBussStopsList(10));
        List<BussStopPointsDTO> list = service.findStopsByLineNumber(1);
        assertEquals("List is empty", createBussStopsList(10).size(), list.size());
    }

    @Test
    public void findLineWithMostStops_moreThanTen() {
        int size = 15;
        when(repository.getAllLineNumbers()).thenReturn(createLineNumbers(size));
        when(repository.findAll()).thenReturn(createJourneyPointList(size));
        when(repository.findStopsByLineNumber(anyInt())).thenReturn(createBussStopsList(size));
        Map<String, Object> map = service.findLineWithMostStops();
        assertEquals("Map is wrong", 2, map.size());
    }

    @Test
    public void findLineWithMostStops_lessThanTen() {
        int size = 5;
        when(repository.getAllLineNumbers()).thenReturn(createLineNumbers(size));
        when(repository.findAll()).thenReturn(createJourneyPointList(size));
        when(repository.findStopsByLineNumber(anyInt())).thenReturn(createBussStopsList(size));
        Map<String, Object> map = service.findLineWithMostStops();
        assertEquals("Map is wrong", 2, map.size());
    }

    @Test
    public void findLineWithMostStops_emptyMap(){
        int size = 0;
        when(repository.getAllLineNumbers()).thenReturn(createLineNumbers(size));
        when(repository.findAll()).thenReturn(createJourneyPointList(size));
        when(repository.findStopsByLineNumber(anyInt())).thenReturn(createBussStopsList(size));
        Map<String, Object> map = service.findLineWithMostStops();
        assertEquals("Map is wrong", 0, map.size());
    }
}