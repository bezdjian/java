package com.bezdjian.trafiklab.controller;

import com.bezdjian.trafiklab.BaseTest;
import com.bezdjian.trafiklab.exception.CustomResponseErrorHandler;
import com.bezdjian.trafiklab.exception.ErrorDetails;
import com.bezdjian.trafiklab.service.TrafikService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
public class BussLineControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BussLineController controller;

    @Mock
    private TrafikService service;

    @Before
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CustomResponseErrorHandler()) //Make use of our custom error handler
                .build();
    }

    @Test
    public void getStopNames() throws Exception {
        //Given
        when(service.findStopsByLineNumber(anyInt())).thenReturn(createBussStopsList(3));
        //When & Expect
        MockHttpServletResponse response = mockMvc.perform(get("/api/getStops/1"))
                //.andDo(print())
                .andExpect(content().string(containsString("here"))) //Since stop name is stop 1
                .andReturn().getResponse();
        //Then
        assertEquals("Response status is wrong", 200, response.getStatus());
    }

    @Test
    public void getMostStops() throws Exception {
        //Given
        when(service.findLineWithMostStops()).thenReturn(createMap());
        //When & Expect
        MockHttpServletResponse response = mockMvc.perform(get("/api/getMostStops"))
                //.andDo(print())
                .andExpect(content().string(containsString("valuez2"))) //Since stop name is stop 1
                .andReturn().getResponse();
        //Then
        assertEquals("Response status is wrong", 200, response.getStatus());
    }

    @Test
    public void testCustomError() throws Exception {
        //Given
        //when(service.findLineWithMostStops()).thenReturn(createMap());
        //When & Expect
        MockHttpServletResponse response = mockMvc.perform(get("/api/getStops/oopie"))
                //.andDo(print())
                .andExpect(content().string(containsString("Failed to convert"))) //Since stop name is stop 1
                .andReturn().getResponse();
        //Then
        assertEquals("Response status is wrong", 400, response.getStatus());
        ErrorDetails err = new ObjectMapper().readValue(response.getContentAsString(), ErrorDetails.class);
        assertEquals("ErrorDetails status is wrong", HttpStatus.BAD_REQUEST.toString(), err.getStatus());
    }
}