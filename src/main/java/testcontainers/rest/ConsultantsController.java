package testcontainers.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import testcontainers.model.*;
import testcontainers.service.*;

@Tag(name = "Consultants")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConsultantsController {

  private final ConsultantService consultantService;
  private final ProjectsClientService projectsClientService;
  private final SqsService sqsService;
  private final SnsService snsService;
  private final S3Service s3Service;

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

  @GetMapping("/consultants/buckets")
  public Flux<BucketResponse> getBuckets() {
    return s3Service.fetchBuckets();
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
