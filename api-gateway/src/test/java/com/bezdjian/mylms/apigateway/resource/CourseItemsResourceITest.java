package com.bezdjian.mylms.apigateway.resource;

import com.bezdjian.mylms.apigateway.client.CourseServiceClient;
import com.bezdjian.mylms.apigateway.client.ItemServiceClient;
import com.bezdjian.mylms.apigateway.dto.CategoryDTO;
import com.bezdjian.mylms.apigateway.dto.CourseDTO;
import com.bezdjian.mylms.apigateway.dto.ItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resources;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseItemsResourceITest {

  private static final String BASE_PATH = "/api/course-items";

  @MockBean
  private CourseServiceClient courseServiceClient;
  @MockBean
  private ItemServiceClient itemServiceClient;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void allCourseItems() throws Exception {
    //Given
    when(courseServiceClient.findAllCourses()).thenReturn(courses());
    when(itemServiceClient.readItems()).thenReturn(items());
    //When
    mockMvc.perform(get(BASE_PATH + "/all"))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn().getResponse();

    //Verify
    verify(courseServiceClient).findAllCourses();
  }

  @Test
  void testCourseFallback() throws Exception {
    //Given
    when(courseServiceClient.findAllCourses()).thenReturn(courses());
    //When
    mockMvc.perform(get(BASE_PATH + "/courseszz"))
      .andExpect(status().isNotFound())
      .andReturn().getResponse();
    //Verify
    verify(courseServiceClient, times(0)).findAllCourses();
    verify(itemServiceClient, times(0)).readItems();
  }

  private Resources<CourseDTO> courses() {
    List<CourseDTO> dtos = new ArrayList<>() {{
      add(CourseDTO.builder()
        .coursename("AWS Developer")
        .category(
          CategoryDTO.builder().name("AWS").build()
        ).build());
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