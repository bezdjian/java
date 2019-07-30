package com.sbab.trafiklab.api;

import com.sbab.trafiklab.BaseTest;
import com.sbab.trafiklab.pojo.JourneyPatternPointOnLine;
import com.sbab.trafiklab.pojo.StopPoint;
import com.sbab.trafiklab.repository.JourneyPointRepository;
import com.sbab.trafiklab.repository.StopPointRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class TrafikLabAPIServiceTest extends BaseTest {

    @InjectMocks
    private TrafikLabAPIService apiService;
    @Mock
    private JourneyPointRepository journeyPointRepository;
    @Mock
    private StopPointRepository stopPointRepository;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void saveJourneyPoints() throws Exception {
        JourneyPatternPointOnLine jppo = getJourneyPatternPointOnLine();
        when(restTemplate.getForObject(anyString(), any())).thenReturn(jppo);
        apiService.saveJourneyPointNumbers();
        verify(journeyPointRepository, times(1)).save(any());
    }

    @Test(expected = Exception.class)
    public void saveJourneyPointsThrowsException() throws Exception {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);
        apiService.saveJourneyPointNumbers();
        verify(journeyPointRepository, times(0)).save(any());
    }

    @Test
    public void saveStopPoints() throws Exception {
        StopPoint stopPoint = createStopPoints();
        when(restTemplate.getForObject(anyString(), any())).thenReturn(stopPoint);
        apiService.saveStopPoints();
        verify(stopPointRepository, times(1)).save(any());
    }

    @Test(expected = Exception.class)
    public void saveStopPointsThrowsException() throws Exception {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);
        apiService.saveStopPoints();
        verify(stopPointRepository, times(0)).save(any());
    }
}