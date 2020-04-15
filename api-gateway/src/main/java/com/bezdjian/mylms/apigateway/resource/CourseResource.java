package com.bezdjian.mylms.apigateway.resource;

import com.bezdjian.mylms.apigateway.dto.CourseDTO;
import com.bezdjian.mylms.apigateway.model.Response;
import com.bezdjian.mylms.apigateway.model.SaveCourseRequest;
import com.bezdjian.mylms.apigateway.services.CourseService;
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

@RestController
@RequestMapping(value = "/api/courses", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Course")
@Slf4j
public class CourseResource {

  private final CourseService courseService;

  @Autowired
  public CourseResource(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping("all")
  public ResponseEntity<Object> allCourses() {
    try {
      log.info("Getting all courses");
      return new ResponseEntity<>(courseService.findAllCourses(), HttpStatus.OK);
    } catch (FeignException e) {
      log.error("Error occurred  when finding all courses. Error: {}", e.getMessage());
      return new ResponseEntity<>(response(e.getMessage(), e.status()),
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{courseId}")
  public ResponseEntity<Object> getCourseById(@PathVariable("courseId") Long courseId) {
    try {
      return new ResponseEntity<>(courseService.findById(courseId), HttpStatus.OK);
    } catch (FeignException e) {
      if (e.status() == HttpStatus.NOT_FOUND.value()) {
        log.info("Course with ID {} not found", courseId);
        return new ResponseEntity<>(response(e.getMessage(), e.status()),
          HttpStatus.valueOf(e.status()));
      }
      log.error("***** Error occurred  when finding a course with ID {}. Error: {}", courseId, e.getMessage());
      return new ResponseEntity<>(response(e.getMessage(), e.status()),
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/save")
  public ResponseEntity<Object> save(@RequestBody SaveCourseRequest courseRequest) {
    log.info("Request save or update course: " + courseRequest.toString());
    try {
      if (courseRequest.getCategoryId() == null) {
        return new ResponseEntity<>(
          response("Category ID must be provided",
            HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
      }

      CourseDTO saved = courseService.save(courseRequest);
      return new ResponseEntity<>(saved, HttpStatus.OK);

    } catch (Exception e) {
      log.error("***** Error during save: {}", e.getMessage(), e);
      return new ResponseEntity<>(response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/delete/{courseId}")
  public ResponseEntity<Response> delete(@PathVariable("courseId") Long courseId) {
    try {
      courseService.delete(courseId);
      log.debug("Course with id {} is deleted", courseId);
      return ResponseEntity.ok(response("Course with ID " + courseId + " has been deleted",
        HttpStatus.OK.value()));
    } catch (FeignException e) {
      log.debug("Error while deleting course {}. Error: {}", courseId, e);
      return new ResponseEntity<>(response(e.getMessage(), e.status()),
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private Response response(String msg, int code) {
    return Response.builder()
      .message(msg)
      .code(code)
      .build();
  }
}
