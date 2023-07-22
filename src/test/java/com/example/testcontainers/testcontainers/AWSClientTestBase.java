package com.example.testcontainers.testcontainers;

import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;

public class AWSClientTestBase {

  static String localstackImageName = "localstack/localstack:latest-arm64";

  @Container
  static LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse(localstackImageName))
      .withServices(LocalStackContainer.Service.S3)
      .withReuse(true);

  private final Region region = Region.of(localStackContainer.getRegion());

  private final String s3LocalstackEndpoint = String.format("http://s3.%s.localstack.cloud:%d", localStackContainer.getHost(),
      localStackContainer.getFirstMappedPort());

  private final String localstackEndpoint = String.format("http://%s:%d", localStackContainer.getHost(),
      localStackContainer.getFirstMappedPort());

  final S3Client amazonS3Client = S3Client
      .builder()
      .region(Region.of(localStackContainer.getRegion()))
      .endpointOverride(URI.create(s3LocalstackEndpoint))
      .credentialsProvider(StaticCredentialsProvider.create(
          AwsBasicCredentials.create(localStackContainer.getAccessKey(), localStackContainer.getSecretKey())))
      .build();

  final SnsClient snsClient = SnsClient.builder()
      .region(region)
      .endpointOverride(URI.create(localstackEndpoint))
      .build();

}
