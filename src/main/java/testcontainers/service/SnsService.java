package testcontainers.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Slf4j
@Service
public class SnsService {

  public static final String TOPIC_NAME = "test-topic";
  private final SnsClient snsClient;

  private final String topicArn;

  public SnsService(SnsClient snsClient) {
    this.snsClient = snsClient;
    topicArn = createTopic();
  }

  public void publishSnsMessage(String message, String subject) {
    PublishResponse publishResponse = snsClient.publish(builder -> builder.message(message)
        .subject(subject)
        .topicArn(topicArn));
    log.info("Published message with id: {}, subject: {}", publishResponse.messageId(), subject);
  }

  private String createTopic() {
    CreateTopicResponse topicResponse = snsClient.createTopic(builder -> builder.name(TOPIC_NAME));
    log.info("Created topic with arn: {}", topicResponse.topicArn());
    return topicResponse.topicArn();
  }
}
