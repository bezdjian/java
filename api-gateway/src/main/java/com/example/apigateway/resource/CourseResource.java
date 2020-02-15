package com.example.apigateway.resource;

import com.example.apigateway.client.CourseServiceClient;
import com.example.apigateway.dto.CourseDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api/courses", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Course")
public class CourseResource {

  private final CourseServiceClient courseClient;

  @Autowired
  public CourseResource(CourseServiceClient courseClient) {
    this.courseClient = courseClient;
  }

  @GetMapping("all")
  public ResponseEntity<Object> allCourses() {
    try {
      Collection<CourseDTO> courses = courseClient.readCourses().getContent();
      return new ResponseEntity<>(courses, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
