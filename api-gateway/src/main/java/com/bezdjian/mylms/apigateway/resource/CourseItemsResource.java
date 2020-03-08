package com.bezdjian.mylms.apigateway.resource;

import com.bezdjian.mylms.apigateway.client.CourseServiceClient;
import com.bezdjian.mylms.apigateway.client.ItemServiceClient;
import com.bezdjian.mylms.apigateway.dto.CourseDTO;
import com.bezdjian.mylms.apigateway.dto.ItemDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/course-items", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Course Items")
public class CourseItemsResource {

  private final CourseServiceClient courseClient;
  private final ItemServiceClient itemServiceClient;

  @Autowired
  public CourseItemsResource(CourseServiceClient courseClient, ItemServiceClient itemServiceClient) {
    this.courseClient = courseClient;
    this.itemServiceClient = itemServiceClient;
  }

  @GetMapping("all")
  public ResponseEntity<Object> allCourseItems() {
    try {
      Collection<CourseDTO> courses = courseClient.findAllCourses().getContent();
      Collection<ItemDTO> items = itemServiceClient.readItems().getContent();
      Map<List<String>, List<String>> map = new HashMap<>();

      List<String> courseNames = new ArrayList<>();
      List<String> itemNames = new ArrayList<>();

      courses.forEach(c -> courseNames.add(c.getCoursename()));
      items.forEach(i -> itemNames.add(i.getName()));

      map.put(courseNames, itemNames);

      return new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
