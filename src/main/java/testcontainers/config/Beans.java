package testcontainers.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.SnsClientBuilder;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.SqsClientBuilder;

import java.net.URI;

@Slf4j
@Configuration
public class Beans {

  @Value("${localstack.url}")
  private String localStackUrl;
  @Value("${localstack.s3.url}")
  private String s3LocalStackUrl;

  @Bean
  public WebClient webClient() {
    return WebClient.create();
  }

  @Bean
  public SnsClient snsClient() {
    SnsClientBuilder snsClient = SnsClient.builder();
    log.info("Using SNS client endpoint: {}", localStackUrl);

    return localStackUrl.equals("default") ? snsClient.build() :
        snsClient.endpointOverride(URI.create(localStackUrl))
                .region(Region.EU_WEST_1)
            .build();
  }

  @Bean
  public SqsClient sqsClient() {
    SqsClientBuilder sqsClient = SqsClient.builder();
    log.info("Using SQS client endpoint: {}", localStackUrl);

    return localStackUrl.equals("default") ? sqsClient.build() :
        sqsClient.endpointOverride(URI.create(localStackUrl))
                .region(Region.EU_WEST_1)
            .build();
  }

  @Bean
  public S3Client s3Client() {
    S3ClientBuilder s3Client = S3Client.builder();
    log.info("Using S3 client endpoint: {}", s3LocalStackUrl);

    return s3LocalStackUrl.equals("default") ? s3Client.build() :
        s3Client.endpointOverride(URI.create(s3LocalStackUrl))
                .region(Region.EU_WEST_1)
                .build();
  }
}
