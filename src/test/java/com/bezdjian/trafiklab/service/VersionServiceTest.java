package com.bezdjian.trafiklab.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class VersionServiceTest {

  @InjectMocks
  private VersionService service;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    service = new VersionService("0.1");
  }

  @Test
  public void version() {
    String v = service.getVersion();
    assertEquals("Version is wrong", "0.1", v);
  }
}