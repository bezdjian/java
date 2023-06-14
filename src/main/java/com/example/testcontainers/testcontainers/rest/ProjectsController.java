package com.example.testcontainers.testcontainers.rest;

import com.example.testcontainers.testcontainers.model.ProjectResponse;
import com.example.testcontainers.testcontainers.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectsController {

  private final ProjectService projectService;

  @GetMapping("/technology/{technology}")
  public Flux<ProjectResponse> findProjectByTechnology(@PathVariable("technology") String technology) {
    return projectService.findProjectByTechnology(technology);
  }
}
