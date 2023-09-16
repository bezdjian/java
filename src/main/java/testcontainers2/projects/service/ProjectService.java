package testcontainers2.projects.service;

import testcontainers2.projects.model.ProjectResponse;
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

  public Flux<ProjectResponse> findAll() {
    return Flux.defer(() -> Flux.fromIterable(makeProjectResponseList()));
  }

  // Database
  private List<ProjectResponse> makeProjectResponseList() {
    return new ArrayList<>() {{
      add(buildProjectResponse("Java", "WirelessCar"));
      add(buildProjectResponse("Java", "Volvo Cars"));
      add(buildProjectResponse("AWS", "WirelessCar"));
      add(buildProjectResponse("Python", "WirelessCar"));
      add(buildProjectResponse("DotNet", "Microsoft"));
      add(buildProjectResponse("NodeJs", "Google"));
      add(buildProjectResponse("TypeScript", "Google"));
      add(buildProjectResponse("Node", "NodeJsCompany"));
      add(buildProjectResponse("JavaScript", "WirelessCar"));
    }};
  }

  private static ProjectResponse buildProjectResponse(String technology, String client) {
    return ProjectResponse.builder()
        .uuid(UUID.randomUUID().toString())
        .name("Project_" + technology)
        .client(client)
        .description("P1 Description")
        .technology(technology)
        .build();
  }
}
