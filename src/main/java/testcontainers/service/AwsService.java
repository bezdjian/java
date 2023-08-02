package testcontainers.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Slf4j
@Service
public class AwsService {

  public static final String TOPIC_NAME = "test-topic";
  public static final String QUEUE_NAME = "test-queue";
  private final SnsClient snsClient;
  private final SqsClient sqsClient;
  private final String topicArn;
  private final String queueUrl;

  public AwsService(SnsClient snsClient, SqsClient sqsClient) {
    this.snsClient = snsClient;
    this.sqsClient = sqsClient;

    topicArn = createTopic();
    queueUrl = createQueue();
  }

  public void publishSnsMessage(String message, String subject) {
    PublishResponse publishResponse = snsClient.publish(builder -> builder.message(message)
        .subject(subject)
        .topicArn(topicArn));
    log.info("Published message with id: {}, subject: {}", publishResponse.messageId(), subject);
  }

  public void sendSqsMessage(String message) {
    SendMessageResponse response = sqsClient.sendMessage(builder -> builder.messageBody(message)
        .queueUrl(queueUrl));
    log.info("Published to SQS message with id: {}", response.messageId());
  }

  private String createTopic() {
    CreateTopicResponse topicResponse = snsClient.createTopic(builder -> builder.name(TOPIC_NAME));
    log.info("Created topic with arn: {}", topicResponse.topicArn());
    return topicResponse.topicArn();
  }

  private String createQueue() {
    CreateQueueResponse queue = sqsClient.createQueue(builder -> builder.queueName(QUEUE_NAME));
    log.info("Created queue with arn: {}", queue.queueUrl());
    return queue.queueUrl();
  }
}
