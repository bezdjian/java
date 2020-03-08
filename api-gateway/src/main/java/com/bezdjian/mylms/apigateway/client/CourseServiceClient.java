package com.bezdjian.mylms.apigateway.client;

import com.bezdjian.mylms.apigateway.dto.CategoryDTO;
import com.bezdjian.mylms.apigateway.dto.CourseDTO;
import com.bezdjian.mylms.apigateway.model.SaveCourse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "course-service") //url =
public interface CourseServiceClient {

  @GetMapping("/course-service/courses")
  Resources<CourseDTO> findAllCourses();

  @GetMapping("/course-service/courses/{courseId}/category")
  CategoryDTO findCategoryByCourse(@PathVariable("courseId") Long courseId);

  @PostMapping("/course-service/courses")
  CourseDTO save(@RequestBody SaveCourse course);

  @GetMapping("/course-service/courses/{courseId}")
  CourseDTO findCourse(@PathVariable("courseId") Long courseId);

  @GetMapping("/course-service/courseCategories/{categoryId}")
  CategoryDTO findCategory(@PathVariable("categoryId") Long categoryId);

  @DeleteMapping("/course-service/courses/{courseId}")
  void deleteCourse(@PathVariable("courseId") Long courseId);
}