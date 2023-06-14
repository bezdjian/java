package com.example.testcontainers.testcontainers.service;

import com.example.testcontainers.testcontainers.model.ProjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectService {

  public Flux<ProjectResponse> findProjectByTechnology(String technology) {
    return Flux.defer(() -> Flux.fromIterable(makeProjectResponseList()
        .stream()
        .filter(p -> p.getTechnology().equals(technology))
        .collect(Collectors.toList())
    ));
  }

  // Database
  private List<ProjectResponse> makeProjectResponseList() {
    return new ArrayList<>() {{
      add(buildProjectResponse("Java"));
      add(buildProjectResponse("Java"));
      add(buildProjectResponse("AWS"));
      add(buildProjectResponse("AWS"));
      add(buildProjectResponse("Python"));
      add(buildProjectResponse("DotNet"));
      add(buildProjectResponse("NodeJs"));
      add(buildProjectResponse("TypeScript"));
      add(buildProjectResponse("Node"));
      add(buildProjectResponse("JavaScript"));
    }};
  }

  private static ProjectResponse buildProjectResponse(String technology) {
    return ProjectResponse.builder()
        .uuid(UUID.randomUUID().toString())
        .name("Project_" + technology)
        .description("P1 Description")
        .technology(technology)
        .build();
  }
}
