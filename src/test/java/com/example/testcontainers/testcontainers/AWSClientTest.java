package com.example.testcontainers.testcontainers;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Testcontainers
class AWSClientTest extends AWSClientTestBase {

  @Order(1)
  @Test
  void shouldCreateS3Bucket() {
    //Given
    String bucketName = "test-bucket";
    CreateBucketRequest request = CreateBucketRequest.builder()
        .bucket(bucketName)
        .build();

    //When
    CreateBucketResponse bucket = amazonS3Client.createBucket(request);

    //Then
    assertEquals(bucket.location(), "/" + bucketName);
  }

  @Order(2)
  @Test
  void shouldListS3Buckets() {
    //When
    List<Bucket> buckets = amazonS3Client.listBuckets().buckets();

    //Then
    assertEquals(1, buckets.size());
    assertEquals("test-bucket", buckets.get(0).name());
  }

  @Test
  void shouldPublishSnsMessage() {
    //Given
    CreateTopicRequest topicRequest = CreateTopicRequest.builder()
        .name("test-topic")
        .build();

    String topicArn = snsClient.createTopic(topicRequest)
        .topicArn();

    PublishRequest request = PublishRequest.builder()
        .message("Hello world")
        .topicArn(topicArn)
        .build();

    //When
    PublishResponse publishResponse = snsClient.publish(request);

    //Then
    assertFalse(publishResponse.messageId().isEmpty());
  }
}
