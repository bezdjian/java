package testcontainers.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@Builder
@ToString
public class ConsultantMessage {

  String messageId;
  String body;
  String receiptHandle;
  List<Map<String, String>> messageAttributes;
}
