package com.example.apigateway.client;

import com.example.apigateway.dto.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("course-service")
public interface CourseServiceClient {

  @GetMapping("/course-service/courses")
    // /items is pre-defined endpoint in item service
  Resources<CourseDTO> readCourses();
}