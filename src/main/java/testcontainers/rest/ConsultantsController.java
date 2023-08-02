package testcontainers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import testcontainers.model.ConsultantMessage;
import testcontainers.model.ConsultantRequest;
import testcontainers.model.ConsultantResponse;
import testcontainers.model.ConsultantsProjectResponse;
import testcontainers.service.ConsultantService;
import testcontainers.service.ProjectsClientService;
import testcontainers.service.SnsService;
import testcontainers.service.SqsService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConsultantsController {

  private final ConsultantService consultantService;
  private final ProjectsClientService projectsClientService;
  private final SqsService sqsService;
  private final SnsService snsService;

  @GetMapping("/consultants")
  public Flux<ConsultantResponse> getAll() {
    return consultantService.findAll();
  }

  @GetMapping("/consultants/messages")
  public Flux<ConsultantMessage> readMessages() {
    return sqsService.getAndDeleteSqsMessage();
  }

  @PostMapping("/consultants")
  public Mono<ConsultantResponse> saveConsultant(@RequestBody ConsultantRequest consultant) {
    return consultantService.save(consultant)
        .map(saved -> {
          String subjectCreated = "CREATED";
          snsService.publishSnsMessage(saved.toString(), subjectCreated);
          sqsService.sendSqsMessage(saved.toString(), subjectCreated);
          return saved;
        });
  }

  @DeleteMapping("/consultants/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable("id") String uuid) {
    snsService.publishSnsMessage(uuid, "DELETED");
    this.consultantService.delete(uuid);
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
