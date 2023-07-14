package com.example.testcontainers.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.images.PullPolicy;

@Slf4j
@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {

  @Bean
  @RestartScope // The Bean (container) is to be reused instead of recreating it with DevTools
  @ServiceConnection
  public MySQLContainer mySQLContainer() {
    return new MySQLContainer();
  }

  // This test communicates to another service test container which by default is disabled.
  //@Bean
  public GenericContainer projectsContainer(DynamicPropertyRegistry registry) {
    final String projectsBaseUrl = "/api/projects";
    GenericContainer projectsService = new GenericContainer("t2")
        .withExposedPorts(8080) // The port that is exposed in Dockerfile of that container.
        .withReuse(true)
        .withImagePullPolicy(PullPolicy.defaultPolicy());

    registry.add("projects.url", () -> {
      String formattedUrl = String.format("http://%s:%d%s", projectsService.getHost(),
          projectsService.getFirstMappedPort(), projectsBaseUrl);
      log.info("***** Formatted URL: " + formattedUrl);
      return formattedUrl;
    });

    projectsService.start();
    return projectsService;
  }
}
