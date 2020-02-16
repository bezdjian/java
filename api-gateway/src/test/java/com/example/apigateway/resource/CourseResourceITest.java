package com.example.apigateway.resource;

import com.example.apigateway.client.CourseServiceClient;
import com.example.apigateway.dto.CourseDTO;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseResourceITest {

  private static final String BASE_PATH = "/api/courses";

  @MockBean
  private CourseServiceClient courseServiceClient;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void allCourses() throws Exception {
    //Given
    when(courseServiceClient.readCourses()).thenReturn(courses());
    //When
    mockMvc.perform(get(BASE_PATH + "/all"))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn().getResponse();

    //Verify
    verify(courseServiceClient).readCourses();
  }

  @Test
  public void findCourse() throws Exception {
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
  public void testCourseFallback() throws Exception {
    //Given
    when(courseServiceClient.readCourses()).thenReturn(courses());
    //When
    mockMvc.perform(get(BASE_PATH + "/courseszz/121"))
      .andExpect(status().isNotFound())
      .andReturn().getResponse();
    //Verify
    verify(courseServiceClient, times(0)).readCourses();
  }

  private Resources<CourseDTO> courses() {
    List<CourseDTO> dtos = new ArrayList<>() {{
      add(new CourseDTO("AWS Developer", "AWS"));
    }};
    return new Resources<>(dtos);
  }
}