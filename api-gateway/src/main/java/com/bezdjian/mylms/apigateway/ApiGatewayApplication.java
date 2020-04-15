package com.bezdjian.mylms.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableZuulProxy
public class ApiGatewayApplication {

  @Value("${eureka.client.serviceUrl.defaultZone}")
  private static String defaultZone;

  public static void main(String[] args) {
    System.out.println("*************** defaultZone from props: " + defaultZone);
    for (String url : args) {
      System.out.println("*************** Eureka Url from args: " + url);
      System.setProperty("env.eureka.url", url);
    }

    SpringApplication.run(ApiGatewayApplication.class, args);
  }
}
