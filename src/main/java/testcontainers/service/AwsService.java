package testcontainers.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.Topic;

import java.util.List;

@Slf4j
@Service
public class AwsService {

  public static final String TOPIC_NAME = "test-topic";
  private final SnsClient snsClient;
  private final String topicArn;

  public AwsService(SnsClient snsClient) {
    this.snsClient = snsClient;
    if (!isTopicExists(TOPIC_NAME)) {
      log.info("Creating topic: {}", TOPIC_NAME);
      topicArn = createTopic(TOPIC_NAME);
    } else {
      log.info("Topic already exists: {}", TOPIC_NAME);
      topicArn = getTopicArn(TOPIC_NAME);
    }
  }

  public void publishMessage(String message, String subject) {
    PublishResponse publishResponse = snsClient.publish(builder -> builder.message(message)
        .subject(subject)
        .topicArn(topicArn));
    log.info("Published message with id: {}, subject: {}", publishResponse.messageId(), subject);
  }

  private String createTopic(String topicName) {
    CreateTopicResponse topicResponse = snsClient.createTopic(builder -> builder.name(topicName));
    log.info("Created topic with arn: {}", topicResponse.topicArn());
    return topicResponse.topicArn();
  }

  private String getTopicArn(String topicName) {
    return getTopicsArn()
        .stream()
        .filter(arn -> arn.contains(topicName))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Topic not found"));
  }

  private boolean isTopicExists(String topicName) {
    return getTopicsArn()
        .stream()
        .anyMatch(arn -> arn.contains(topicName));
  }

  private List<String> getTopicsArn() {
    return snsClient.listTopics()
        .topics()
        .stream()
        .map(Topic::topicArn)
        .toList();
  }
}
