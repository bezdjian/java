package testcontainers.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import testcontainers.entity.Consultant;
import testcontainers.model.ConsultantsProjectResponse;
import testcontainers.model.ProjectResponse;
import testcontainers.repository.ConsultantRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectsClientService {

  @Value("${projects.url}")
  private String projectsUrl;

  private final WebClient webClient;
  private final ConsultantRepository consultantRepository;

  private static ConsultantsProjectResponse mapProjectResponse(ProjectResponse project, Consultant consultant) {
    return ConsultantsProjectResponse.builder()
        .consultantId(consultant.getId().toString())
        .projectName(project.getName())
        .consultantName(consultant.getName())
        .technology(project.getTechnology())
        .description(project.getDescription())
        .build();
  }

  public Flux<ConsultantsProjectResponse> findConsultantsByProjectTechnology(String technology) {
    Flux<ProjectResponse> projects = getProjectsByTechnology(technology);

    return projects
        .doOnNext(p -> log.info("*** Got project {}, {}", p.getName(), p.getTechnology()))
        .map(project -> consultantRepository.findConsultantByTechnology(technology)
            .filter(consultant -> consultant.getTechnology().equals(project.getTechnology()))
            .map(consultant -> mapProjectResponse(project, consultant)))
        .distinct()
        .flatMap(f -> f);
  }

  private Flux<ProjectResponse> getProjectsByTechnology(String technology) {
    String path = projectsUrl + "/technology/{technology}";
    log.info("******* projectsUrl: " + path);
    return webClient.get()
        .uri(path, technology)
        .retrieve()
        .bodyToFlux(ProjectResponse.class);
  }
}
