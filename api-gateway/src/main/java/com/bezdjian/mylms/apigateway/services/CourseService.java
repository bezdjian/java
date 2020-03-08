package com.bezdjian.mylms.apigateway.services;

import com.bezdjian.mylms.apigateway.client.CourseServiceClient;
import com.bezdjian.mylms.apigateway.dto.CategoryDTO;
import com.bezdjian.mylms.apigateway.dto.CourseDTO;
import com.bezdjian.mylms.apigateway.model.SaveCourse;
import com.bezdjian.mylms.apigateway.model.SaveCourseRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class CourseService {

  private final CourseServiceClient courseClient;
  @Value("${course.service.url}")
  private String courseServiceUrl;
  @Value("${course.service.port}")
  private String courseServicePort;

  @Autowired
  public CourseService(CourseServiceClient courseClient) {
    this.courseClient = courseClient;
  }

  public Collection<CourseDTO> findAllCourses() {
    Collection<CourseDTO> courses = courseClient.findAllCourses().getContent();
    courses.forEach(course -> {
      CategoryDTO category = courseClient.findCategoryByCourse(course.getId());
      course.setCategory(category);
    });
    return courses;
  }

  public CourseDTO findById(Long courseId) {
    CourseDTO course = courseClient.findCourse(courseId);
    CategoryDTO category = courseClient.findCategoryByCourse(courseId);
    course.setCategory(category);
    return course;
  }

  public CourseDTO save(SaveCourseRequest course) {
    // Calling findCategory to make sure the method goes through without error and that means
    // the category exists.
    CategoryDTO category = courseClient.findCategory(course.getCategoryId());
    String categoryUri = courseServiceUrl + ":" + courseServicePort + "/course-service/courseCategories/" + category.getId();

    SaveCourse saveCourse = SaveCourse.builder()
      .coursename(course.getCoursename())
      .description(course.getDescription())
      .idnumber(course.getIdnumber())
      .image(course.getImage())
      .price(course.getPrice())
      .category(categoryUri)
      .build();

    CourseDTO savedCourse = courseClient.save(saveCourse);
    savedCourse.setCategory(category);
    return savedCourse;
  }

  public void delete(Long courseId) {
    courseClient.deleteCourse(courseId);
  }
}
