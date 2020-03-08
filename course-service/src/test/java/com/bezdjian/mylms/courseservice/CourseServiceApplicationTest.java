package com.bezdjian.mylms.courseservice;

import com.bezdjian.mylms.courseservice.repository.CourseCategoryRepository;
import com.bezdjian.mylms.courseservice.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CourseServiceApplicationTest {

  private static final String BASE_PATH = "";
  @Autowired
  private CourseRepository courseRepository;
  @Autowired
  private CourseCategoryRepository courseCategoryRepository;

  @Test
  void saveCourse() {
  }
}
