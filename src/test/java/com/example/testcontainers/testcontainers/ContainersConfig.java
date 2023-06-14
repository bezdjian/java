package com.example.testcontainers.testcontainers;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {

  @Bean
  @RestartScope // The Bean (container) is to be reused instead of recreating it with DevTools
  @ServiceConnection
  public MySQLContainer mySQLContainer() {
    return new MySQLContainer();
  }
}
