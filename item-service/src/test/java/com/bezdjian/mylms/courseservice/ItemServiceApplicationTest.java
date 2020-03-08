package com.bezdjian.mylms.courseservice;

import com.bezdjian.mylms.itemservice.ItemServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ItemServiceApplication.class)
@ActiveProfiles("test")
public class ItemServiceApplicationTest {

  @Test
  public void contextLoads() {
  }
}
