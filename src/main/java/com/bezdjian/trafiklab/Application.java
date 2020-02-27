package com.bezdjian.trafiklab;

import com.bezdjian.trafiklab.client.TrafikLabAPIClient;
import com.bezdjian.trafiklab.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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
        saveAllPoints();
    }

    /**
     * Private method to call during construction that saves the data from TrafikLab's API.
     *
     * @throws ClientException when nothing to save.
     */
    private void saveAllPoints() throws ClientException {
        client.saveJourneyPointNumbers();
        client.saveStopPoints();
    }
}
