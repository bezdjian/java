package testcontainers.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import testcontainers.entity.Consultant;
import testcontainers.model.ConsultantsProjectResponse;
import testcontainers.model.ProjectResponse;
import testcontainers.repository.ConsultantRepository;

import java.util.List;

@Slf4j
@Service
public class ProjectsClientService {

  @Value("${projects.url}")
  private String projectsUrl;

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private ConsultantRepository consultantRepository;

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
    List<ProjectResponse> projects = getProjectsByTechnology(technology);

    return Flux.fromIterable(
        projects.stream()
            .peek(p -> log.info("*** Got project {}, {}", p.getName(), p.getTechnology()))
            .flatMap(project -> consultantRepository.findConsultantsByTechnology(technology)
                .stream()
                .filter(consultant -> consultant.getTechnology().equals(project.getTechnology()))
                .map(consultant -> mapProjectResponse(project, consultant)))
            .distinct()
            .toList());
  }

  private List<ProjectResponse> getProjectsByTechnology(String technology) {
    String path = projectsUrl + "/technology/" + technology;
    log.info("******* projectsUrl: " + path);
    return restTemplate.exchange(path,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<ProjectResponse>>() {
            })
        .getBody();
  }
}
