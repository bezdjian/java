package com.bezdjian.trafiklab.client;

import com.bezdjian.trafiklab.TestUtils;
import com.bezdjian.trafiklab.model.StopPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TrafficLabClientServiceTest {

    @InjectMocks
    private TrafficLabClientService trafficLabClientService;
    @Mock
    private TrafficClient trafficClient;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void getJourneyPointNumbers() throws Exception {
        when(trafficClient.getJourneyPatternPointOnLine())
            .thenReturn(Mono.just(TestUtils.getJourneyPatternPointOnLine()));
        trafficLabClientService.getJourneyPoints();

        verify(trafficClient).getJourneyPatternPointOnLine();
    }

    @Test
    void getStopPoints() {
        //Given
        when(trafficClient.getStopPoints())
            .thenReturn(Mono.just(StopPoint.builder()
                .responseData(TestUtils.createStopPoints().getResponseData())
                .build()));

        //When
        StopPoint stopPoint = trafficLabClientService.getStopPoints().block();

        //Then
        assertNotNull(stopPoint);
        assertNotNull(stopPoint.getResponseData());
        assertEquals(3, stopPoint.getResponseData().getResult().size());
        verify(trafficClient).getStopPoints();
    }
}