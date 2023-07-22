package com.example.testcontainers.testcontainers.cucumber;


import com.example.testcontainers.testcontainers.ContainersConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ContainersConfig.class})
@CucumberContextConfiguration
public class CucumberSpringConfiguration {

}
