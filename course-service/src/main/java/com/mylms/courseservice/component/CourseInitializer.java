package com.mylms.courseservice.component;

import com.mylms.courseservice.entity.Course;
import com.mylms.courseservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Slf4j
public class CourseInitializer implements CommandLineRunner {

  private final CourseRepository courseRepository;

  public CourseInitializer(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public void run(String... args) {
    // Create some Strings and save as Item.
    Stream.of("AWS Solutions Architect", "AWS Developer", "AWS SysOps", "AWS DevOps", "AI", "Java 11")
      .forEach(course -> courseRepository.save(new Course(course)));

    //Find all Items
    log.info("***** Displaying courses:");
    courseRepository.findAll().forEach(
      course -> log.info("Course: {}", course.getCoursename())
    );
    log.info("***** EO: Displaying courses:");
  }
}
