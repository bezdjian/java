package testcontainers.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {

  public static final String fullImageName = "mysql:8";
  static String localstackImageName = "localstack/localstack:latest-arm64";

  @Bean
  @RestartScope // The Bean (container) is to be reused instead of recreating it with DevTools
  @ServiceConnection
  public MySQLContainer<?> mySQLContainer() {
    return new MySQLContainer<>(DockerImageName.parse(fullImageName));
  }

  @Bean
  public LocalStackContainer localStackContainer(DynamicPropertyRegistry registry) {
    LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse(localstackImageName))
        .withReuse(true);

    registry.add("localstack.url", () -> String.format("http://%s:%d",
        localStackContainer.getHost(),
        localStackContainer.getFirstMappedPort()));
    registry.add("localstack.sns.endpoint", () -> String.format("http://%s:%d",
        localStackContainer.getHost(),
        localStackContainer.getFirstMappedPort()));
    registry.add("localstack.s3.url", () -> String.format("http://s3.%s.localstack.cloud:%d",
        localStackContainer.getHost(),
        localStackContainer.getFirstMappedPort()));

    localStackContainer.start();
    return localStackContainer;
  }

  // This test communicates to another service test container which by default is disabled.
  //@Bean
  public GenericContainer<?> projectsContainer(DynamicPropertyRegistry registry) {
    final String projectsBaseUrl = "/api/projects";
    GenericContainer<?> projectsServiceContainer = new GenericContainer("t2")
        .withExposedPorts(8080) // The port that is exposed in Dockerfile of that container.
        .withReuse(true)
        .withImagePullPolicy(PullPolicy.defaultPolicy());

    registry.add("projects.url", () -> {
      String formattedUrl = String.format("http://%s:%d%s",
          projectsServiceContainer.getHost(),
          projectsServiceContainer.getFirstMappedPort(),
          projectsBaseUrl);
      log.info("***** Formatted URL: " + formattedUrl);
      return formattedUrl;
    });

    projectsServiceContainer.start();
    return projectsServiceContainer;
  }
}
