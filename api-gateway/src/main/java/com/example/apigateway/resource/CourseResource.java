package com.example.apigateway.resource;

import com.example.apigateway.client.CourseServiceClient;
import com.example.apigateway.dto.CategoryDTO;
import com.example.apigateway.dto.CourseDTO;
import com.example.apigateway.model.Response;
import com.example.apigateway.model.SaveCourse;
import feign.FeignException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api/courses", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Course")
@Slf4j
public class CourseResource {

  private final CourseServiceClient courseClient;

  @Autowired
  public CourseResource(CourseServiceClient courseClient) {
    this.courseClient = courseClient;
  }

  @GetMapping("all")
  public ResponseEntity<Object> allCourses() {
    try {
      Collection<CourseDTO> courses = courseClient.findAllCourses().getContent();
      courses.forEach(course -> {
        CategoryDTO category = courseClient.findCategoryByCourse(course.getId());
        course.setCategory(category);
      });
      return new ResponseEntity<>(courses, HttpStatus.OK);
    } catch (FeignException e) {
      log.error("Error occurred  when finding all courses. Error: {}", e.getMessage());
      return new ResponseEntity<>(response(
        "Error while finding all courses. " + e.getMessage(),
        e.status()
      ), HttpStatus.valueOf(e.status()));
    }
  }

  @GetMapping("/{courseId}")
  public ResponseEntity<Object> getCourseById(@PathVariable("courseId") Long courseId) {
    try {
      CourseDTO course = courseClient.findCourse(courseId);
      CategoryDTO category = courseClient.findCategoryByCourse(courseId);
      course.setCategory(category);
      return new ResponseEntity<>(course, HttpStatus.OK);
    } catch (FeignException e) {
      log.error("Error occurred  when finding a course with ID {}. Error: {}", courseId, e.getMessage());
      return new ResponseEntity<>(response(
        "Course " + courseId + " not found",
        e.status()
      ), HttpStatus.valueOf(e.status()));
    }
  }

  @PostMapping("/save")
  public ResponseEntity<Object> save(@RequestBody SaveCourse course) {
    log.info("Request save or update course: " + course.toString());
    try {
      if (course.getCategory() == null || course.getCategory().getId() == null)
        return new ResponseEntity<>(response("Category ID must be provided", HttpStatus.BAD_REQUEST.value())
          , HttpStatus.BAD_REQUEST);

      CategoryDTO category = courseClient.findCategory(course.getCategory().getId());
      course.setCategory(category);
      CourseDTO saved = courseClient.saveCourse(course);
      return ResponseEntity.ok(saved);

    } catch (FeignException e) {
      log.error("***** Error during save: {}", e.getMessage(), e);
      return new ResponseEntity<>(response(
        "Error during save: " + e.getMessage(),
        e.status()
      ), HttpStatus.valueOf(e.status()));
    }
  }

  @DeleteMapping("/delete/{courseId}")
  public ResponseEntity<Response> delete(@PathVariable("courseId") Long courseId) {
    try {
      courseClient.deleteCourse(courseId);
      log.debug("Course with id {} is deleted", courseId);
      return ResponseEntity.ok(Response.builder()
        .message("Course has been deleted")
        .code(HttpStatus.OK.value())
        .build());
    } catch (FeignException e) {
      log.debug("Error while deleting course {}. Error: {}", courseId, e);
      return new ResponseEntity<>(response(
        "Error while deleting course: " + e.getMessage(),
        e.status()
      ), HttpStatus.valueOf(e.status()));
    }
  }

  private Response response(String msg, int code) {
    return Response.builder()
      .message(msg)
      .code(code)
      .build();
  }
}
