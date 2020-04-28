package com.bezdjian.trafiklab;

import com.bezdjian.trafiklab.client.TrafikLabAPIClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableCaching
@Slf4j
public class Application implements CommandLineRunner {

  @Autowired
  private TrafikLabAPIClient client;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    // Do any additional configuration here
    return new RestTemplate();
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("***** Initializing TrafikService and saving stop points *****");
    client.saveJourneyPointNumbers();
    client.saveStopPoints();
  }

  @Bean
  public WebMvcConfigurer cors() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000");
      }
    };
  }
}
