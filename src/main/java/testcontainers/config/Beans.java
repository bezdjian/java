package testcontainers.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.SnsClientBuilder;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.SqsClientBuilder;

import java.net.URI;

@Slf4j
@Configuration
public class Beans {

  @Value("${localstack.sns.endpoint}")
  private String snsEndpoint;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public SnsClient snsClient() {
    SnsClientBuilder snsClientBuilder = SnsClient.builder();
    log.info("Using SNS client endpoint: {}", snsEndpoint);

    return snsEndpoint.equals("default") ? snsClientBuilder.build() :
        snsClientBuilder.endpointOverride(URI.create(snsEndpoint))
            .build();
  }

  @Bean
  public SqsClient sqsClient() {
    SqsClientBuilder sqsClient = SqsClient.builder();
    log.info("Using SQS client endpoint: {}", snsEndpoint);

    return snsEndpoint.equals("default") ? sqsClient.build() :
        sqsClient.endpointOverride(URI.create(snsEndpoint))
            .build();
  }
}
