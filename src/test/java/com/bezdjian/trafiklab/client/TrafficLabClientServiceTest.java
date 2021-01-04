package com.bezdjian.trafiklab.client;

import com.bezdjian.trafiklab.TestUtils;
import com.bezdjian.trafiklab.model.StopPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class TrafficLabClientServiceTest {

    private static final String API_MOCKED_KEY = "kl123jcslk2";
    @InjectMocks
    private TrafficLabClientService trafficLabClientService;
    @Mock
    private TrafficLabClient trafficLabClient;

    @BeforeEach
    void setup() {
        initMocks(this);
        ReflectionTestUtils.setField(trafficLabClientService, "key", API_MOCKED_KEY);
    }

    @Test
    void getJourneyPointNumbers() throws Exception {
        when(trafficLabClient.getJourneyPatternPointOnLine(API_MOCKED_KEY))
                .thenReturn(TestUtils.getJourneyPatternPointOnLine());
        trafficLabClientService.getJourneyPoints();

        verify(trafficLabClient).getJourneyPatternPointOnLine(API_MOCKED_KEY);
    }

    @Test
    void getStopPoints() throws Exception {
        when(trafficLabClient.getStopPoints(API_MOCKED_KEY)).thenReturn(StopPoint.builder()
                .responseData(TestUtils.createStopPoints().getResponseData())
                .build());
        trafficLabClientService.getStopPoints();
        verify(trafficLabClient).getStopPoints(API_MOCKED_KEY);
    }
}