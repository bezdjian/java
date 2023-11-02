package testcontainers.integration;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import software.amazon.awssdk.services.sns.SnsClient;
import testcontainers.config.ContainersConfig;
import testcontainers.model.BucketResponse;
import testcontainers.service.S3Service;
import testcontainers.service.SnsService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Profile("docker")
@SpringBootTest(classes = {ContainersConfig.class})
class AWSClientTest {

  @Autowired
  private S3Service s3Service;
  @Autowired
  private SnsService snsService;
  @Mock
  private SnsClient snsClient;

  @Test
  void shouldFetchS3Bucket() {
    //When
    Flux<BucketResponse> bucket = s3Service.fetchBuckets();

    //Then
    StepVerifier.create(bucket)
        .assertNext(b -> assertEquals("test-bucket-service", b.name()))
        .verifyComplete();
  }

  @Test
  void shouldPublishSnsMessage() {
    //Given
    String message = "Hello world";

    //When & Then
    assertDoesNotThrow(() -> snsService.publishSnsMessage(message, "test-topic"));
  }
}
