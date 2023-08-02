package testcontainers.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import testcontainers.model.ConsultantMessage;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SqsService {

  public static final String QUEUE_NAME = "test-queue";
  public static final String ACTION = "ACTION";
  private static final int NUMBER_OF_MESSAGES = 10;

  private final String queueUrl;
  private final SqsClient sqsClient;

  public SqsService(SqsClient sqsClient) {
    this.sqsClient = sqsClient;
    queueUrl = createQueue();
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
        .messageAttributes(messageAttributes)
        .build();
  }

  private Map<String, String> toKeyValuePair(Map.Entry<String, MessageAttributeValue> e) {
    return Map.of(e.getKey(), e.getValue().stringValue());
  }

  private String createQueue() {
    CreateQueueResponse queue = sqsClient.createQueue(builder -> builder.queueName(QUEUE_NAME));
    log.info("Created queue with arn: {}", queue.queueUrl());
    return queue.queueUrl();
  }

}
