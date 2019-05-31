package com.sbab.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
public class BussLinesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BussLinesApplication.class, args);
	}


    @Bean
    public RestTemplate restTemplate() {
        // Do any additional configuration here
        return new RestTemplate();
    }
}
