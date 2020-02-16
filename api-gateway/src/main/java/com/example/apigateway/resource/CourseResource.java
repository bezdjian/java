package com.example.apigateway.resource;

import com.example.apigateway.client.CourseServiceClient;
import com.example.apigateway.dto.CourseDTO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
      Collection<CourseDTO> courses = courseClient.readCourses().getContent();
      return new ResponseEntity<>(courses, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Error occurred  when finding all courses. Error: {}", e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  /*
  @PostMapping("/save")
  public ResponseEntity<Object> save(@RequestBody CourseRequest request) {
    //TODO: Make it prettier
    log.info("Request save or update course: " + request.toString());
    try {
      if (request.getCategory() == null)
        throw new CourseCategoryNotFoundException("Category ID must be provided");

      CourseCategory cc = courseCategoryService.findById(request.getCategory());
      CourseEntity saved = courseService.save(request);
      saved.setCourseCategory(cc);
      //Re-save for course category.. maybe better solution?
      courseService.purge(saved);
      return ResponseEntity.ok(new CourseDTO(saved));
    } catch (Exception e) {
      log.error("***** Error during save: " + e);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
   */

  @GetMapping("/{courseId}")
  public ResponseEntity<Object> getCourseById(@PathVariable("courseId") Long courseId) {
    try {
      Collection<CourseDTO> course = courseClient.findCourse(courseId).getContent();
      return new ResponseEntity<>(course, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Error occurred  when finding a course with ID {}. Error: {}", courseId, e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /*@DeleteMapping("/delete/{courseId}")
  public ResponseEntity<CourseDto> delete(@PathVariable("courseId") Long courseId) {
    CourseEntity course = courseService.delete(courseId);
    log.warn("Course with id {} is deleted", courseId);
    return ResponseEntity.ok(new CourseDto(course));
  }*/
}
