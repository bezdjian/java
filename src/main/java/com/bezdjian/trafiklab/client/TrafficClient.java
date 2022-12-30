package com.bezdjian.trafiklab.client;

import com.bezdjian.trafiklab.model.JourneyPatternPointOnLine;
import com.bezdjian.trafiklab.model.StopPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TrafficClient {

  private final String key;
  private final WebClient client;

  public TrafficClient(@Value("${trafiklab.api.url}") String baseUrl,
                       @Value("${trafiklab.api.key}") String key) {
    this.key = key;
    this.client = WebClient.builder()
      .baseUrl(baseUrl)
      .codecs(codec -> codec.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
      .build();
  }

  public Mono<StopPoint> getStopPoints() {
    return this.client
      .get()
      .uri("/LineData.json?model=stop&key=" + key)
      .exchangeToMono(response -> {
        if (response.statusCode().isError()) {
          return response.createException().flatMap(Mono::error);
        }
        return response.bodyToMono(StopPoint.class);
      });
  }

  public Mono<JourneyPatternPointOnLine> getJourneyPatternPointOnLine() {
    return this.client
      .get()
      .uri("/LineData.json?model=jour&key=" + key)
      .exchangeToMono(response -> {
        if (response.statusCode().isError()) {
          return response.createException().flatMap(Mono::error);
        }
        return response.bodyToMono(JourneyPatternPointOnLine.class);
      });
  }
}
