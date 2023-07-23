package testcontainers;

import org.springframework.boot.SpringApplication;
import testcontainers.config.ContainersConfig;

public class TestApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(Application::main)
        .with(ContainersConfig.class)
        .run(args);
  }
}
