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

    public static final String DEFAULT_URL = "default";
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
        if (!localStackUrl.equals(DEFAULT_URL)) {
            log.info("Using localstack SNS client with endpoint: {}", localStackUrl);
            snsClient.endpointOverride(URI.create(localStackUrl))
                    .region(Region.EU_WEST_1)
                    .build();
        }

        log.info("Using default SNS client");
        return snsClient.build();

    }

    @Bean
    public SqsClient sqsClient() {
        SqsClientBuilder sqsClient = SqsClient.builder();
        if (!localStackUrl.equals(DEFAULT_URL)) {
            log.info("Using localstack SQS client with endpoint: {}", localStackUrl);
            return sqsClient.endpointOverride(URI.create(localStackUrl))
                    .region(Region.EU_WEST_1)
                    .build();
        }
        log.info("Using default SQS client");
        return sqsClient.build();

    }

    @Bean
    public S3Client s3Client() {
        S3ClientBuilder s3Client = S3Client.builder();
        if (!s3LocalStackUrl.equals(DEFAULT_URL)) {
            log.info("Using localstack S3 client with endpoint: {}", s3LocalStackUrl);
            s3Client.endpointOverride(URI.create(s3LocalStackUrl))
                    .region(Region.EU_WEST_1)
                    .build();
        }
        log.info("Using default S3 client");
        return s3Client.build();
    }
}
