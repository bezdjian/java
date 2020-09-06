package com.bezdjian.mylms.apigateway.resource;

import com.bezdjian.mylms.apigateway.client.CourseServiceClient;
import com.bezdjian.mylms.apigateway.model.Category;
import com.bezdjian.mylms.apigateway.model.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resources;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CourseResourceITest {

  private static final String BASE_PATH = "/api/courses";

  @MockBean
  private CourseServiceClient courseServiceClient;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void allCourses() throws Exception {
    //Given
    when(courseServiceClient.findAllCourses()).thenReturn(courses());
    //When
    mockMvc.perform(get(BASE_PATH + "/all"))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn().getResponse();

    //Verify
    verify(courseServiceClient).findAllCourses();
  }

  @Test
  void findCourse() throws Exception {
    //Given
    when(courseServiceClient.findCourse(anyLong())).thenReturn(courses().getContent().iterator().next());
    //When
    mockMvc.perform(get(BASE_PATH + "/1"))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn().getResponse();

    //Verify
    verify(courseServiceClient).findCourse(anyLong());
  }

  @Test
  void testCourseFallback() throws Exception {
    //Given
    when(courseServiceClient.findAllCourses()).thenReturn(courses());
    //When
    mockMvc.perform(get(BASE_PATH + "/courseszz/121"))
      .andExpect(status().isNotFound())
      .andReturn().getResponse();
    //Verify
    verify(courseServiceClient, times(0)).findAllCourses();
  }

  private Resources<Course> courses() {
    List<Course> dtos = new ArrayList<>() {{
      add(Course.builder()
        .courseName("AWS Developer")
        .category(
          Category.builder().name("AWS").build()
        ).build());
    }};
    return new Resources<>(dtos);
  }
}