package testcontainers.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.SnsClientBuilder;

import java.net.URI;

@Slf4j
@Configuration
public class Beans {

  @Value("${localstack.sns.endpoint}")
  private String snsEndpoint;

  @Bean
  public SnsClient snsClient() {
    SnsClientBuilder snsClientBuilder = SnsClient.builder();
    log.info("Using SNS client endpoint: {}", snsEndpoint);

    return snsEndpoint.equals("default") ? snsClientBuilder.build() :
        snsClientBuilder.endpointOverride(URI.create(snsEndpoint))
            .build();
  }
}
