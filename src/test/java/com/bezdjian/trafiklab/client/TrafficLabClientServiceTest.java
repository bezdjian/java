package com.bezdjian.trafiklab.client;

import com.bezdjian.trafiklab.TestUtils;
import com.bezdjian.trafiklab.model.JourneyPatternPointOnLine;
import com.bezdjian.trafiklab.model.StopPoint;
import com.bezdjian.trafiklab.repository.JourneyPointRepository;
import com.bezdjian.trafiklab.repository.StopPointRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TrafficLabClientServiceTest {

    @InjectMocks
    private TrafficLabClientService trafficLabClientService;
    @Mock
    private TrafficLabClient trafficLabClient;
    @Mock
    private JourneyPointRepository journeyPointRepository;
    @Mock
    private StopPointRepository stopPointRepository;

    @Before
    public void setup() {
        initMocks(this);
        ReflectionTestUtils.setField(trafficLabClientService, "key", "keyzz");
        /*trafficLabClientService = new TrafficLabClientService(trafficLabClient, journeyPointRepository,
                stopPointRepository, "keyz");*/
    }

    @Test
    public void saveJourneyPoints() throws Exception {
        JourneyPatternPointOnLine jppo = TestUtils.getJourneyPatternPointOnLine();
        when(trafficLabClient.getJourneyPatternPointOnLine(anyString())).thenReturn(jppo);
        trafficLabClientService.saveJourneyPointNumbers();
        verify(journeyPointRepository, times(1)).save(any());
    }

    @Test(expected = Exception.class)
    public void saveJourneyPointsThrowsException() throws Exception {
        trafficLabClientService.saveJourneyPointNumbers();
        verify(journeyPointRepository, times(0)).save(any());
    }

    @Test
    public void saveStopPoints() throws Exception {
        StopPoint stopPoint = TestUtils.createStopPoints();
        when(trafficLabClient.getStopPoints(anyString())).thenReturn(stopPoint);
        trafficLabClientService.saveStopPoints();
        verify(stopPointRepository, times(1)).save(any());
    }

    @Test(expected = Exception.class)
    public void saveStopPointsThrowsException() throws Exception {
        trafficLabClientService.saveStopPoints();
        verify(stopPointRepository, times(0)).save(any());
    }
}