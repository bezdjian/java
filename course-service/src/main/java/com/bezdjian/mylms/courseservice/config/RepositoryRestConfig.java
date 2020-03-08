package com.bezdjian.mylms.courseservice.config;

import com.bezdjian.mylms.courseservice.entity.Course;
import com.bezdjian.mylms.courseservice.entity.CourseCategory;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RepositoryRestConfig implements RepositoryRestConfigurer {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(Course.class, CourseCategory.class);
  }

}
