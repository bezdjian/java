package com.mylms.courseservice.repository;

import com.mylms.courseservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
// Endpoint localhost:8082/courses from api-gateway comes here because of @RepositoryRestResource!! AWSome!
public interface CourseRepository extends JpaRepository<Course, Long> {
}
