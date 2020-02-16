package com.example.apigateway.client;

import com.example.apigateway.dto.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("course-service")
public interface CourseServiceClient {

  @GetMapping("/course-service/courses")
    // /items is pre-defined endpoint in item service
  Resources<CourseDTO> readCourses();

  @GetMapping("/course-service/courses/{courseId}")
    // /items is pre-defined endpoint in item service
  CourseDTO findCourse(@PathVariable("courseId") Long courseId);

  @DeleteMapping("/course-service/courses/{courseId}")
    // /items is pre-defined endpoint in item service
  Resources<CourseDTO> deleteCourse(@PathVariable("courseId") Long courseId);
}