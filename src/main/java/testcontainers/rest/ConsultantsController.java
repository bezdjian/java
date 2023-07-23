package testcontainers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import testcontainers.model.ConsultantRequest;
import testcontainers.model.ConsultantResponse;
import testcontainers.model.ConsultantsProjectResponse;
import testcontainers.service.ConsultantService;
import testcontainers.service.ProjectsClientService;

@RestController
@RequestMapping("/api/consultants")
public class ConsultantsController {

  @Autowired
  private ConsultantService consultantService;
  @Autowired
  private ProjectsClientService projectsClientService;

  @GetMapping("/")
  public Flux<ConsultantResponse> getAll() {
    return consultantService.findAll();
  }

  @PostMapping("/")
  public Mono<ConsultantResponse> saveConsultant(@RequestBody ConsultantRequest consultant) {
    return consultantService.save(consultant);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable("id") String uuid) {
    consultantService.delete(uuid);
  }

  @GetMapping("/technology/{technology}")
  public Flux<ConsultantResponse> findConsultantsByTechnology(@PathVariable("technology") String technology) {
    return consultantService.findConsultantsByTechnology(technology);
  }

  @GetMapping("/projects/{technology}")
  public Flux<ConsultantsProjectResponse> findConsultantsByProjectTechnology(@PathVariable("technology") String technology) {
    return projectsClientService.findConsultantsByProjectTechnology(technology);
  }
}
