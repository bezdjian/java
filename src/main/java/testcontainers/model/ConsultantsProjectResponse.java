package testcontainers.model;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantsProjectResponse {

  private String consultantId;
  private String consultantName;
  private String projectName;
  private String technology;
  private String description;
}
