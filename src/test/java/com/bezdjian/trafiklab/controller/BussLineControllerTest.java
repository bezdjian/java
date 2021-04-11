package com.bezdjian.trafiklab.controller;

import com.bezdjian.trafiklab.TestUtils;
import com.bezdjian.trafiklab.service.TrafficService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
class BussLineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BussLineController controller;

    @Mock
    private TrafficService service;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getStopNames() throws Exception {
        //Given
        when(service.findStopsByLineNumber(anyInt())).thenReturn(TestUtils.createBussStopsList(3));
        //When & Expect
        MockHttpServletResponse response = mockMvc.perform(get("/api/getStops/1"))
            //.andDo(print())
            .andExpect(content().string(containsString("here"))) //Since stop name is stop 1
            .andReturn().getResponse();
        //Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getMostStops() throws Exception {
        //Given
        when(service.findLineWithMostStops()).thenReturn(TestUtils.createMap());
        //When & Expect
        MockHttpServletResponse response = mockMvc.perform(get("/api/getMostStops"))
            //.andDo(print())
            .andExpect(content().string(containsString("valuez2"))) //Since stop name is stop 1
            .andReturn().getResponse();
        //Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}