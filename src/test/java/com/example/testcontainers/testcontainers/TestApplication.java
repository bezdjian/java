package com.example.testcontainers.testcontainers;

import org.springframework.boot.SpringApplication;

public class TestApplication {

  public static void main(String[] args) {
    SpringApplication
        .from(Application::main)
        .with(ContainersConfig.class)
        .run(args);
  }
}
