package com.bezdjian.trafiklab.client;

import com.bezdjian.trafiklab.BaseTest;
import com.bezdjian.trafiklab.pojo.JourneyPatternPointOnLine;
import com.bezdjian.trafiklab.pojo.StopPoint;
import com.bezdjian.trafiklab.repository.JourneyPointRepository;
import com.bezdjian.trafiklab.repository.StopPointRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class TrafikLabAPIClientTest extends BaseTest {

    private TrafikLabAPIClient apiClient;
    @Mock
    private JourneyPointRepository journeyPointRepository;
    @Mock
    private StopPointRepository stopPointRepository;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        initMocks(this);
        apiClient = new TrafikLabAPIClient(journeyPointRepository, stopPointRepository,
                "key", "http://test", restTemplate);
    }

    @Test
    public void saveJourneyPoints() throws Exception {
        JourneyPatternPointOnLine jppo = getJourneyPatternPointOnLine();
        when(restTemplate.getForObject(anyString(), any())).thenReturn(
                jppo);
        apiClient.saveJourneyPointNumbers();
        verify(journeyPointRepository, times(1)).save(any());
    }

    @Test(expected = Exception.class)
    public void saveJourneyPointsThrowsException() throws Exception {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);
        apiClient.saveJourneyPointNumbers();
        verify(journeyPointRepository, times(0)).save(any());
    }

    @Test
    public void saveStopPoints() throws Exception {
        StopPoint stopPoint = createStopPoints();
        when(restTemplate.getForObject(anyString(), any())).thenReturn(stopPoint);
        apiClient.saveStopPoints();
        verify(stopPointRepository, times(1)).save(any());
    }

    @Test(expected = Exception.class)
    public void saveStopPointsThrowsException() throws Exception {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);
        apiClient.saveStopPoints();
        verify(stopPointRepository, times(0)).save(any());
    }
}