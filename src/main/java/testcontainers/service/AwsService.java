package testcontainers.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import testcontainers.model.ConsultantMessage;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AwsService {

  public static final String TOPIC_NAME = "test-topic";
  public static final String QUEUE_NAME = "test-queue";
  private static final int NUMBER_OF_MESSAGES = 10;
  public static final String ACTION = "ACTION";
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

  public void sendSqsMessage(String message, String action) {
    Map<String, MessageAttributeValue> messageAttributes = Map.of(ACTION, MessageAttributeValue.builder()
            .stringValue(action)
            .dataType("String")
            .build(),
        "AnotherAction", MessageAttributeValue.builder()
            .stringValue("SomeValueForAnotherAction")
            .dataType("String")
            .build()
    );

    SendMessageResponse response = sqsClient.sendMessage(builder -> builder.messageBody(message)
        .messageAttributes(messageAttributes)
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

  public Flux<ConsultantMessage> getAndDeleteSqsMessage() {
    ReceiveMessageResponse sqsMessage = sqsClient.receiveMessage(builder ->
        builder.queueUrl(queueUrl)
            .maxNumberOfMessages(NUMBER_OF_MESSAGES)
            .messageAttributeNames("All")
    );

    return Flux.defer(() -> Flux.fromIterable(sqsMessage.messages()
        .stream()
        .peek(this::logAndDelete)
        .map(this::mapToConsultantMessage)
        .toList()));
  }

  private void logAndDelete(Message message) {
    log.info("Received message: {} with message attributes {}", message.body(), message.messageAttributes());
    log.info("Deleting message: {}", message.messageId());
    sqsClient.deleteMessage(builder -> builder.queueUrl(queueUrl)
        .receiptHandle(message.receiptHandle()));
  }

  private ConsultantMessage mapToConsultantMessage(Message message) {
    List<Map<String, String>> messageAttributes = message.messageAttributes()
        .entrySet()
        .stream()
        .map(this::toKeyValuePair)
        .toList();

    return ConsultantMessage.builder()
        .messageId(message.messageId())
        .body(message.body())
        .receiptHandle(message.receiptHandle())
        .messageAttributes(messageAttributes)
        .build();
  }

  private Map<String, String> toKeyValuePair(Map.Entry<String, MessageAttributeValue> e) {
    return Map.of(e.getKey(), e.getValue().stringValue());
  }
}
