package com.bezdjian.mylms.apigateway.services;

import com.bezdjian.mylms.apigateway.client.CourseServiceClient;
import com.bezdjian.mylms.apigateway.dto.CategoryDTO;
import com.bezdjian.mylms.apigateway.dto.CourseDTO;
import com.bezdjian.mylms.apigateway.model.SaveCourse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class CourseService {

  private final CourseServiceClient courseClient;

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

  public CourseDTO save(SaveCourse course) {
    CategoryDTO category = courseClient.findCategory(course.getCategory().getId());
    course.setCategory(category);
    return courseClient.saveCourse(course);
  }

  public void delete(Long courseId) {
    courseClient.deleteCourse(courseId);
  }
}
