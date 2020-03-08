package com.bezdjian.mylms.apigateway.resource;

import com.bezdjian.mylms.apigateway.services.CourseItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/course-items", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Course Items")
public class CourseItemsResource {

  private final CourseItemService courseItemService;

  @Autowired
  public CourseItemsResource(CourseItemService courseItemService) {
    this.courseItemService = courseItemService;
  }

  @GetMapping("all")
  public ResponseEntity<Object> allCourseItems() {
    try {
      Map<List<String>, List<String>> map = courseItemService.allCourseItems();
      return new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}