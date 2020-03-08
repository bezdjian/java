package com.mylms.courseservice.component;

import com.mylms.courseservice.entity.Course;
import com.mylms.courseservice.entity.CourseCategory;
import com.mylms.courseservice.repository.CourseCategoryRepository;
import com.mylms.courseservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Stream;

@Component
@Slf4j
public class CourseInitializer implements CommandLineRunner {

  private final CourseRepository courseRepository;
  private final CourseCategoryRepository courseCategoryCourseRepository;

  public CourseInitializer(CourseRepository courseRepository, CourseCategoryRepository courseCategoryCourseRepository) {
    this.courseRepository = courseRepository;
    this.courseCategoryCourseRepository = courseCategoryCourseRepository;
  }

  @Override
  public void run(String... args) {
    // Create some Strings and save as course category FIRST.
    Stream.of("AWS", "Java") // AWS ID 1 -- JAVA ID 2
      .forEach(category ->
        courseCategoryCourseRepository.save(new CourseCategory(category, category + "_desc"))
      );

    // Create some Strings and save as course with category ids.
    Map.of("AWS Solutions Architect", 1L,
      "AWS Developer", 1L,
      "AWS SysOps", 1L,
      "AWS DevOps", 1L,
      "Java 11", 2L,
      "Spring Boot", 2L)
    //.entrySet()
    .forEach(
      (course, categoryId) -> {
        CourseCategory category = new CourseCategory();
        category.setId(categoryId);
        courseRepository.save(new Course(course, "desc", "idnumber", category));
      }
    );

    //Find all courses
    log.info("***** Displaying courses:");
    courseRepository.findAll().forEach(
      course -> log.info("Course: {} ", course.toString())
    );
    log.info("***** EO: Displaying courses:");
  }
}
