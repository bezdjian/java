package testcontainers2.projects.rest;

import testcontainers2.projects.model.ProjectResponse;
import testcontainers2.projects.service.ProjectService;
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

  @GetMapping("/technology")
  public Flux<ProjectResponse> findAll() {
    return projectService.findAll();
  }
}
