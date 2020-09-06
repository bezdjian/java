package com.bezdjian.mylms.apigateway.services;

import com.bezdjian.mylms.apigateway.client.CourseServiceClient;
import com.bezdjian.mylms.apigateway.model.Category;
import com.bezdjian.mylms.apigateway.model.Course;
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

  public Collection<Course> findAllCourses() {
    Collection<Course> courses = courseClient.findAllCourses().getContent();
    courses.forEach(course -> {
      Category category = courseClient.findCategoryByCourse(course.getId());
      course.setCategory(category);
    });
    return courses;
  }

  public Course findById(Long courseId) {
    return courseClient.findCourse(courseId);
  }

  public Course save(SaveCourseRequest course) {
    // Calling findCategory to make sure the method goes through without error and
    // that means the category exists.
    Category category = courseClient.findCategory(course.getCategoryId());
    String categoryUri = courseServiceUrl + ":" + courseServicePort + "/course-service/courseCategories/"
      + category.getId();
    log.info("Getting category from {}", categoryUri);

    SaveCourse saveCourse = SaveCourse.builder()
      .coursename(course.getCoursename())
      .description(course.getDescription())
      .idnumber(course.getIdnumber())
      .image(course.getImage())
      .price(course.getPrice())
      .category(categoryUri)
      .id(course.getId())
      .build();

    Course savedCourse = courseClient.save(saveCourse);
    savedCourse.setCategory(category);
    return savedCourse;
  }

  public void delete(Long courseId) {
    courseClient.deleteCourse(courseId);
  }
}
