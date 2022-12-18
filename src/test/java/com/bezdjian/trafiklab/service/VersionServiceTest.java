package com.bezdjian.trafiklab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.openMocks;

class VersionServiceTest {

    private static final String VERSION = "0.1";
    @InjectMocks
    private VersionService service;

    @BeforeEach
    void setUp() {
        openMocks(this);
        service = new VersionService(VERSION);
    }

    @Test
    void version() {
        String v = service.getVersion().block().getVersion();
        assertEquals(VERSION, v);
    }
}