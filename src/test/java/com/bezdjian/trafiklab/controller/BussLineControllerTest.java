package com.bezdjian.trafiklab.controller;

import com.bezdjian.trafiklab.TestUtils;
import com.bezdjian.trafiklab.service.TrafficService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@WebFluxTest
class BussLineControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TrafficService service;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getStopNames() {
        //Given
        when(service.findStopsByLineNumber(anyInt())).thenReturn(Mono.just(TestUtils.createBussStopsList(3)));
        //When & Expect
        webTestClient.get()
            .uri("/api/getStops/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(List.class)
            .value(v -> assertEquals(3, v.size()));
    }

    @Test
    void getMostStops() {
        //Given
        when(service.findLineWithMostStops()).thenReturn(Mono.just(TestUtils.createMap()));
        //When & Expect
        webTestClient.get()
            .uri("/api/getMostStops")
            .exchange()
            .expectStatus().isOk()
            //.andExpect(content().string(containsString("valuez2"))) //Since stop name is stop 1
            .expectBody(Map.class)
            .value(map -> assertEquals(2, map.size()));
    }
}