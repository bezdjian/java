package testcontainers.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketAlreadyOwnedByYouException;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import testcontainers.model.BucketResponse;

@Slf4j
@Service
public class S3Service {

  public static final String BUCKET_NAME = "test-bucket-service";

  private final S3Client s3Client;

  public S3Service(S3Client s3Client) {
    this.s3Client = s3Client;
    createBucket();
  }

  public Flux<BucketResponse> fetchBuckets() {
    ListBucketsResponse response = s3Client.listBuckets();
    return Flux.defer(() -> Flux.fromIterable(response.buckets()))
        .map(bucket -> new BucketResponse(bucket.name()));
  }

  private void createBucket() {
    try {
      CreateBucketResponse response = s3Client.createBucket(builder -> builder.bucket(BUCKET_NAME));
      log.info("Created bucket with arn: {}", response.location());
    } catch (BucketAlreadyOwnedByYouException e) {
      log.info("Bucket {} already owned, skipping creation...", BUCKET_NAME);
    }
  }
}
