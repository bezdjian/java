package com.example.apigateway.resource;

import com.example.apigateway.client.CourseServiceClient;
import com.example.apigateway.client.ItemServiceClient;
import com.example.apigateway.dto.CourseDTO;
import com.example.apigateway.dto.ItemDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resources;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseItemsResourceITest {

  private static final String BASE_PATH = "/api/course-items";

  @MockBean
  private CourseServiceClient courseServiceClient;
  @MockBean
  private ItemServiceClient itemServiceClient;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void allCourseItems() throws Exception {
    //Given
    when(courseServiceClient.readCourses()).thenReturn(courses());
    when(itemServiceClient.readItems()).thenReturn(items());
    //When
    mockMvc.perform(get(BASE_PATH + "/all"))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn().getResponse();

    //Verify
    verify(courseServiceClient).readCourses();
  }

  @Test
  public void testCourseFallback() throws Exception {
    //Given
    when(courseServiceClient.readCourses()).thenReturn(courses());
    //When
    mockMvc.perform(get(BASE_PATH + "/courseszz"))
      .andExpect(status().isNotFound())
      .andReturn().getResponse();
    //Verify
    verify(courseServiceClient, times(0)).readCourses();
    verify(itemServiceClient, times(0)).readItems();
  }

  private Resources<CourseDTO> courses() {
    List<CourseDTO> dtos = new ArrayList<>() {{
      add(new CourseDTO("AWS Developer", "AWS"));
    }};
    return new Resources<>(dtos);
  }

  private Resources<ItemDTO> items() {
    List<ItemDTO> dtos = new ArrayList<>() {{
      add(new ItemDTO("Amazon"));
    }};
    return new Resources<>(dtos);
  }
}